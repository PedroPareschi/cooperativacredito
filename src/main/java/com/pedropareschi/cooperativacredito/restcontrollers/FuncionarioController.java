package com.pedropareschi.cooperativacredito.restcontrollers;

import com.pedropareschi.cooperativacredito.domain.Empresa;
import com.pedropareschi.cooperativacredito.domain.Funcionario;
import com.pedropareschi.cooperativacredito.dto.FuncionarioDTO;
import com.pedropareschi.cooperativacredito.services.EmpresaService;
import com.pedropareschi.cooperativacredito.services.FuncionarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/funcionarios")
public class FuncionarioController {
    private final FuncionarioService funcionarioService;
    private final EmpresaService empresaService;


    @Autowired
    public FuncionarioController(FuncionarioService funcionarioService, EmpresaService empresaService) {
        this.funcionarioService = funcionarioService;
        this.empresaService = empresaService;
    }

    @GetMapping
    public ResponseEntity<List<Funcionario>> getAllFuncionarios() {
        List<Funcionario> funcionarios = funcionarioService.getAllFuncionarios();
        return ResponseEntity.ok(funcionarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Funcionario> getFuncionarioById(@PathVariable Long id) {
        Funcionario funcionario = funcionarioService.getFuncionarioById(id);
        return ResponseEntity.ok(funcionario);
    }

    @PostMapping
    public ResponseEntity<Funcionario> createFuncionario(@Valid @RequestBody FuncionarioDTO funcionarioDTO) {
        Funcionario funcionario = convertDtoToEntity(funcionarioDTO);
        Funcionario createdFuncionario = funcionarioService.createFuncionario(funcionario);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFuncionario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Funcionario> updateFuncionario(@PathVariable Long id, @Valid @RequestBody FuncionarioDTO updatedFuncionarioDTO) {
        Funcionario updatedFuncionario = convertDtoToEntity(updatedFuncionarioDTO);
        Funcionario updatedFuncionarioEntity = funcionarioService.updateFuncionario(id, updatedFuncionario);
        return ResponseEntity.ok(updatedFuncionarioEntity);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFuncionario(@PathVariable Long id) {
        funcionarioService.deleteFuncionario(id);
        return ResponseEntity.noContent().build();
    }

    private Funcionario convertDtoToEntity(FuncionarioDTO funcionarioDTO) {
        Funcionario funcionario = new Funcionario();
        funcionario.setNome(funcionarioDTO.getNome());
        funcionario.setCpf(funcionarioDTO.getCpf());
        funcionario.setSalario(funcionarioDTO.getSalario());
        funcionario.setTemNomeLimpo(funcionarioDTO.isTemNomeLimpo());
        funcionario.setTotalDevido(0);
        Empresa empresa = empresaService.getEmpresaById(funcionarioDTO.getEmpresaId());
        funcionario.setEmpresa(empresa);
        return funcionario;
    }
}