package com.pedropareschi.cooperativacredito.restcontrollers;

import com.pedropareschi.cooperativacredito.domain.*;
import com.pedropareschi.cooperativacredito.domain.enums.SituacaoPagamento;
import com.pedropareschi.cooperativacredito.dto.BemDuravelDTO;
import com.pedropareschi.cooperativacredito.dto.ContratoDTO;
import com.pedropareschi.cooperativacredito.services.ContratoService;
import com.pedropareschi.cooperativacredito.services.FuncionarioService;
import com.pedropareschi.cooperativacredito.services.PagamentoService;
import com.pedropareschi.cooperativacredito.services.VendedorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/contratos")
public class ContratoController {
    private final ContratoService contratoService;
    private final FuncionarioService funcionarioService;
    private final VendedorService vendedorService;
    private final PagamentoService pagamentoService;

    @Autowired
    public ContratoController(ContratoService contratoService, FuncionarioService funcionarioService, VendedorService vendedorService, PagamentoService pagamentoService) {
        this.contratoService = contratoService;
        this.funcionarioService = funcionarioService;
        this.vendedorService = vendedorService;
        this.pagamentoService = pagamentoService;
    }

    @GetMapping
    public ResponseEntity<List<Contrato>> getAllContratos() {
        List<Contrato> contratos = contratoService.getAllContratos();
        return ResponseEntity.ok(contratos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Contrato> getContratoById(@PathVariable Long id) {
        Contrato contrato = contratoService.getContratoById(id);
        return ResponseEntity.ok(contrato);
    }

    @PostMapping
    public ResponseEntity<Contrato> createContrato(@Valid @RequestBody ContratoDTO contratoDTO) {
        Funcionario funcionario = funcionarioService.getFuncionarioById(contratoDTO.getFuncionarioId());
        Vendedor vendedor = vendedorService.getVendedorById(contratoDTO.getVendedorId());

        if (!funcionario.isTemNomeLimpo()) {
            throw new IllegalArgumentException("Funcionário não tem o nome limpo.");
        }

        if (contratoDTO.getMesesParcelamento() < 1 || contratoDTO.getMesesParcelamento() > 24) {
            throw new IllegalArgumentException("Prazo final excede o limite máximo de 24 meses.");
        }

        Contrato contrato = convertDtoToEntity(contratoDTO, funcionario, vendedor);
        testarPagamento(contrato);
        Contrato createdContrato = contratoService.createContrato(contrato);
        criarPagamentos(contrato);
        createdContrato.setPagamentos(pagamentoService.getPagamentosByContrato(contrato));
        return ResponseEntity.status(HttpStatus.CREATED).body(createdContrato);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContrato(@PathVariable Long id) {
        pagamentoService.deleteAllFromContrato(id);
        contratoService.deleteContrato(id);
        return ResponseEntity.noContent().build();
    }

    private Contrato convertDtoToEntity(ContratoDTO contratoDTO, Funcionario funcionario, Vendedor vendedor) {
        Contrato contrato = new Contrato();
        contrato.setTaxaDeJuros(contratoDTO.getTaxaDeJuros());
        contrato.setMesesParcelamento(contratoDTO.getMesesParcelamento());
        contrato.setDataContrato(new Date());
        contrato.setFuncionario(funcionario);
        contrato.setVendedor(vendedor);
        BemDuravel bemDuravel = new BemDuravel();
        BemDuravelDTO bemDuravelDTO = contratoDTO.getBemDuravel();
        bemDuravel.setNome(bemDuravelDTO.getNome());
        bemDuravel.setValor(bemDuravelDTO.getValor());
        contrato.setValorPrimeiraParcela(bemDuravel.getValor() / contrato.getMesesParcelamento());
        contrato.setBemDuravel(bemDuravel);
        return contrato;
    }

    private void testarPagamento(Contrato contrato) {
        Pagamento pagamentoTeste = new Pagamento();
        pagamentoTeste.setPosicao(contrato.getMesesParcelamento()-1);
        pagamentoTeste.setContrato(contrato);
        Funcionario funcionario = contrato.getFuncionario();
        if (pagamentoTeste.getValor() + funcionario.getTotalDevido() > funcionario.getMargemConsignada()) {
            throw new IllegalArgumentException("Valor do pagamento ultrapassa a margem consignada: " + pagamentoTeste.getValor() + ". Margem consignada: " + contrato.getFuncionario().getMargemConsignada());
        }
        else{
            funcionario.setTotalDevido(funcionario.getTotalDevido() + pagamentoTeste.getValor());
            funcionarioService.updateFuncionario(funcionario.getId(), funcionario);
        }
    }


    private void criarPagamentos(Contrato contrato) {
        for (int i = 0; i < contrato.getMesesParcelamento(); i++) {
            Pagamento pagamento = new Pagamento();
            pagamento.setContrato(contrato);
            pagamento.setSituacaoPagamento(SituacaoPagamento.PENDENTE);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(contrato.getDataContrato());
            calendar.add(Calendar.MONTH, i);
            pagamento.setMesCobranca(calendar.getTime());
            pagamento.setPosicao(i);
            pagamentoService.createPagamento(pagamento);
        }
    }
}