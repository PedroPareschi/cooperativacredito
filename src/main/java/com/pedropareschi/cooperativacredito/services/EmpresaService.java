package com.pedropareschi.cooperativacredito.services;

import com.pedropareschi.cooperativacredito.domain.Empresa;
import com.pedropareschi.cooperativacredito.exceptions.ResourceNotFoundException;
import com.pedropareschi.cooperativacredito.repositories.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmpresaService {
    private final EmpresaRepository repository;

    @Autowired
    public EmpresaService(EmpresaRepository repository) {
        this.repository = repository;
    }

    public List<Empresa> getAllEmpresas() {
        return repository.findAll();
    }

    public Empresa getEmpresaById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Empresa não encontrada com id: " + id));
    }

    public Empresa createEmpresa(Empresa empresa) {
        return repository.save(empresa);
    }

    public Empresa updateEmpresa(Long id, Empresa updatedEmpresa) {
        Empresa existingEmpresa = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Empresa não encontrada com id: " + id));

        existingEmpresa.setNome(updatedEmpresa.getNome());
        existingEmpresa.setCnpj(updatedEmpresa.getCnpj());
        existingEmpresa.setComissao(updatedEmpresa.getComissao());
        existingEmpresa.setFuncionarios(updatedEmpresa.getFuncionarios());

        return repository.save(existingEmpresa);
    }

    public void deleteEmpresa(Long id) {
        Optional<Empresa> empresa = repository.findById(id);
        empresa.ifPresent(repository::delete);
    }
}