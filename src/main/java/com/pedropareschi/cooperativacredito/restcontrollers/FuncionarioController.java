package com.pedropareschi.cooperativacredito.restcontrollers;

import com.pedropareschi.cooperativacredito.domain.Funcionario;
import com.pedropareschi.cooperativacredito.dto.FuncionarioDTO;
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

    @Autowired
    public FuncionarioController(FuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
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
    public ResponseEntity<Funcionario> createFuncionario(@Valid @RequestBody FuncionarioDTO funcionario) {
        Funcionario createdFuncionario = funcionarioService.createFuncionario(funcionario);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFuncionario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Funcionario> updateFuncionario(@PathVariable Long id, @Valid @RequestBody FuncionarioDTO updatedFuncionario) {
        Funcionario updatedFuncionarioEntity = funcionarioService.updateFuncionario(id, updatedFuncionario);
        return ResponseEntity.ok(updatedFuncionarioEntity);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFuncionario(@PathVariable Long id) {
        funcionarioService.deleteFuncionario(id);
        return ResponseEntity.noContent().build();
    }
}