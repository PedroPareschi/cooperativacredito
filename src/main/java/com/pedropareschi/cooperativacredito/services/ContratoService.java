package com.pedropareschi.cooperativacredito.services;

import com.pedropareschi.cooperativacredito.domain.Contrato;
import com.pedropareschi.cooperativacredito.exceptions.ResourceNotFoundException;
import com.pedropareschi.cooperativacredito.repositories.ContratoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContratoService {
    private final ContratoRepository contratoRepository;

    @Autowired
    public ContratoService(ContratoRepository contratoRepository) {
        this.contratoRepository = contratoRepository;
    }

    public List<Contrato> getAllContratos() {
        return contratoRepository.findAll();
    }

    public Contrato getContratoById(Long id) {
        return contratoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contrato não encontrado com id: " + id));
    }

    public Contrato createContrato(Contrato contrato) {
        return contratoRepository.save(contrato);
    }

    public void deleteContrato(Long id) {
        if (!contratoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Contrato não encontrado com id: " + id);
        }
        contratoRepository.deleteById(id);
    }
}