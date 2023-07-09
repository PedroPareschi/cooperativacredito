package com.pedropareschi.cooperativacredito.restcontrollers;

import com.pedropareschi.cooperativacredito.domain.BemDuravel;
import com.pedropareschi.cooperativacredito.domain.Contrato;
import com.pedropareschi.cooperativacredito.domain.Funcionario;
import com.pedropareschi.cooperativacredito.domain.Vendedor;
import com.pedropareschi.cooperativacredito.dto.BemDuravelDTO;
import com.pedropareschi.cooperativacredito.dto.ContratoDTO;
import com.pedropareschi.cooperativacredito.services.ContratoService;
import com.pedropareschi.cooperativacredito.services.FuncionarioService;
import com.pedropareschi.cooperativacredito.services.VendedorService;
import jakarta.validation.Valid;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/contratos")
public class ContratoController {
    private final ContratoService contratoService;
    private final FuncionarioService funcionarioService;
    private final VendedorService vendedorService;

    @Autowired
    public ContratoController(ContratoService contratoService, FuncionarioService funcionarioService, VendedorService vendedorService) {
        this.contratoService = contratoService;
        this.funcionarioService = funcionarioService;
        this.vendedorService = vendedorService;
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

        Date prazoMaximo = DateUtils.addMonths(new Date(), 24);
        if (contratoDTO.getPrazoFinal().after(prazoMaximo)) {
            throw new IllegalArgumentException("Prazo final excede o limite máximo de 24 meses.");
        }

        Contrato contrato = convertDtoToEntity(contratoDTO, funcionario, vendedor);
        Contrato createdContrato = contratoService.createContrato(contrato);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdContrato);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Contrato> updateContrato(@PathVariable Long id, @Valid @RequestBody ContratoDTO contratoDTO) {
        Funcionario funcionario = funcionarioService.getFuncionarioById(contratoDTO.getFuncionarioId());
        Vendedor vendedor = vendedorService.getVendedorById(contratoDTO.getVendedorId());

        if (!funcionario.isTemNomeLimpo()) {
            throw new IllegalArgumentException("Funcionário não tem o nome limpo.");
        }

        Date prazoMaximo = DateUtils.addMonths(new Date(), 24);
        if (contratoDTO.getPrazoFinal().after(prazoMaximo)) {
            throw new IllegalArgumentException("Prazo final excede o limite máximo de 24 meses.");
        }

        Contrato contrato = convertDtoToEntity(contratoDTO, funcionario, vendedor);
        contrato.setId(id);
        Contrato updatedContrato = contratoService.updateContrato(contrato);
        return ResponseEntity.ok(updatedContrato);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContrato(@PathVariable Long id) {
        contratoService.deleteContrato(id);
        return ResponseEntity.noContent().build();
    }

    private Contrato convertDtoToEntity(ContratoDTO contratoDTO, Funcionario funcionario, Vendedor vendedor) {
        Contrato contrato = new Contrato();
        contrato.setTaxaDeJuros(contratoDTO.getTaxaDeJuros());
        contrato.setPrazoFinal(contratoDTO.getPrazoFinal());
        contrato.setFuncionario(funcionario);
        contrato.setVendedor(vendedor);
        BemDuravel bemDuravel = new BemDuravel();
        BemDuravelDTO bemDuravelDTO = contratoDTO.getBemDuravel();
        bemDuravel.setNome(bemDuravelDTO.getNome());
        bemDuravel.setValor(bemDuravelDTO.getValor());
        contrato.setBemDuravel(bemDuravel);

        return contrato;
    }
}