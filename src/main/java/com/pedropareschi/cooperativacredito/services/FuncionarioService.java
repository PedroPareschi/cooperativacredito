package com.pedropareschi.cooperativacredito.services;

import com.pedropareschi.cooperativacredito.domain.Empresa;
import com.pedropareschi.cooperativacredito.domain.Funcionario;
import com.pedropareschi.cooperativacredito.dto.FuncionarioDTO;
import com.pedropareschi.cooperativacredito.exceptions.ResourceNotFoundException;
import com.pedropareschi.cooperativacredito.repositories.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FuncionarioService {
    private final FuncionarioRepository repository;
    private final EmpresaService empresaService;

    @Autowired
    public FuncionarioService(FuncionarioRepository repository, EmpresaService empresaService) {
        this.repository = repository;
        this.empresaService = empresaService;
    }

    public List<Funcionario> getAllFuncionarios() {
        return repository.findAll();
    }

    public Funcionario getFuncionarioById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Funcionario not found with id: " + id));
    }

    public Funcionario createFuncionario(FuncionarioDTO funcionarioDTO) {
        Funcionario funcionario = convertDtoToEntity(funcionarioDTO);
        return repository.save(funcionario);
    }

    public Funcionario updateFuncionario(Long id, FuncionarioDTO updatedFuncionarioDTO) {
        Funcionario existingFuncionario = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Funcionario not found with id: " + id));
        Funcionario updatedFuncionario = convertDtoToEntity(updatedFuncionarioDTO);
        updatedFuncionario.setId(id);
        return repository.save(updatedFuncionario);
    }

    public void deleteFuncionario(Long id) {
        Optional<Funcionario> funcionario = repository.findById(id);
        funcionario.ifPresent(repository::delete);
    }

    private Funcionario convertDtoToEntity(FuncionarioDTO funcionarioDTO) {
        Funcionario funcionario = new Funcionario();
        funcionario.setNome(funcionarioDTO.getNome());
        funcionario.setCpf(funcionarioDTO.getCpf());
        funcionario.setSalario(funcionarioDTO.getSalario());
        funcionario.setTemNomeLimpo(funcionarioDTO.isTemNomeLimpo());
        Empresa empresa = empresaService.getEmpresaById(funcionarioDTO.getEmpresaId());
        funcionario.setEmpresa(empresa);
        return funcionario;
    }

}