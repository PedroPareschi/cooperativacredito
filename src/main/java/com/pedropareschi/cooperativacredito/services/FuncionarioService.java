package com.pedropareschi.cooperativacredito.services;

import com.pedropareschi.cooperativacredito.domain.Funcionario;
import com.pedropareschi.cooperativacredito.exceptions.ResourceNotFoundException;
import com.pedropareschi.cooperativacredito.repositories.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FuncionarioService {
    private final FuncionarioRepository repository;

    @Autowired
    public FuncionarioService(FuncionarioRepository repository) {
        this.repository = repository;
    }

    public List<Funcionario> getAllFuncionarios() {
        return repository.findAll();
    }

    public Funcionario getFuncionarioById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Funcionario não encontrado com id: " + id));
    }

    public Funcionario createFuncionario(Funcionario funcionario) {
        return repository.save(funcionario);
    }

    public Funcionario updateFuncionario(Long id, Funcionario funcionario) {
        Funcionario existingFuncionario = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Funcionario não encontrado com id: " + id));
        funcionario.setId(id);
        return repository.save(funcionario);
    }

    public void deleteFuncionario(Long id) {
        Optional<Funcionario> funcionario = repository.findById(id);
        funcionario.ifPresent(repository::delete);
    }

}