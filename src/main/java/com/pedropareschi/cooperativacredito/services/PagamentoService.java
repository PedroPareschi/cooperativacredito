package com.pedropareschi.cooperativacredito.services;

import com.pedropareschi.cooperativacredito.domain.Contrato;
import com.pedropareschi.cooperativacredito.domain.Pagamento;
import com.pedropareschi.cooperativacredito.domain.enums.SituacaoPagamento;
import com.pedropareschi.cooperativacredito.exceptions.ResourceNotFoundException;
import com.pedropareschi.cooperativacredito.repositories.PagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PagamentoService {
    private final PagamentoRepository repository;

    @Autowired
    public PagamentoService(PagamentoRepository repository) {
        this.repository = repository;
    }

    public Pagamento getPagamentoById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pagamento não encontrado com id: " + id));
    }

    public Pagamento createPagamento(Pagamento pagamento){
        return repository.save(pagamento);
    }

    public void deleteAllFromContrato(Long idContrato) {
        repository.deleteAllByContratoId(idContrato);
    }

    public Pagamento updatePagamento(Long id, SituacaoPagamento situacaoPagamento) {
        Pagamento pagamento  = getPagamentoById(id);
        pagamento.setSituacaoPagamento(situacaoPagamento);
        return repository.save(pagamento);
    }

    public Pagamento getPagamentoByPosicaoAndContrato(int posicao, Contrato contrato) {
        return repository.getPagamentoByPosicaoAndContrato(posicao, contrato.getId());
    }
    public List<Pagamento> getPagamentosByContrato(Contrato contrato){
        return repository.getPagamentosByContrato(contrato.getId());
    }
}
