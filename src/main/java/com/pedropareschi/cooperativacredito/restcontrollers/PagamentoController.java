package com.pedropareschi.cooperativacredito.restcontrollers;

import com.pedropareschi.cooperativacredito.domain.*;
import com.pedropareschi.cooperativacredito.domain.enums.SituacaoPagamento;
import com.pedropareschi.cooperativacredito.dto.PagamentoDTO;
import com.pedropareschi.cooperativacredito.services.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {

    private final PagamentoService service;
    private final FuncionarioService funcionarioService;

    @Autowired
    public PagamentoController(PagamentoService service, FuncionarioService funcionarioService) {
        this.service = service;
        this.funcionarioService = funcionarioService;
    }


    @PutMapping("/{id}")
    public ResponseEntity<Pagamento> updatePagamento(@PathVariable Long id, @Valid @RequestBody PagamentoDTO pagamentoDTO) {
        SituacaoPagamento situacaoPagamento = SituacaoPagamento.fromCod(pagamentoDTO.getSituacaoPagamento());
        if (situacaoPagamento == null) {
            throw new IllegalArgumentException("Código inválido");
        }
        Pagamento updatedPagamento = service.updatePagamento(id, situacaoPagamento);
        if (pagamentoDTO.getSituacaoPagamento() == 1) {
            Pagamento pagamentoAnterior = service.getPagamentoByPosicaoAndContrato(updatedPagamento.getPosicao() - 1, updatedPagamento.getContrato());
            if (pagamentoAnterior.getSituacaoPagamento().equals(SituacaoPagamento.ATRASADO)) {
                Funcionario funcionario = updatedPagamento.getContrato().getFuncionario();
                funcionario.setTemNomeLimpo(false);
                funcionarioService.updateFuncionario(funcionario.getId(), funcionario);
            }
        }
        return ResponseEntity.ok(updatedPagamento);
    }
}
