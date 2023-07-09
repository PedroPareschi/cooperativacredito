package com.pedropareschi.cooperativacredito.restcontrollers;

import com.pedropareschi.cooperativacredito.domain.Empresa;
import com.pedropareschi.cooperativacredito.services.EmpresaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/empresas")
public class EmpresaController {
    private final EmpresaService service;

    @Autowired
    public EmpresaController(EmpresaService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Empresa>> getAllEmpresas() {
        List<Empresa> empresas = service.getAllEmpresas();
        return ResponseEntity.ok(empresas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Empresa> getEmpresaById(@PathVariable Long id) {
        Empresa empresa = service.getEmpresaById(id);
        return ResponseEntity.ok(empresa);
    }

    @PostMapping
    public ResponseEntity<Empresa> createEmpresa(@Valid @RequestBody Empresa empresa) {
        Empresa createdEmpresa = service.createEmpresa(empresa);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEmpresa);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Empresa> updateEmpresa(@PathVariable Long id, @Valid @RequestBody Empresa updatedEmpresa) {
        Empresa updatedEmpresaEntity = service.updateEmpresa(id, updatedEmpresa);
        return ResponseEntity.ok(updatedEmpresaEntity);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmpresa(@PathVariable Long id) {
        service.deleteEmpresa(id);
        return ResponseEntity.noContent().build();
    }
}


