package com.pedropareschi.cooperativacredito.restcontrollers;

import com.pedropareschi.cooperativacredito.domain.Vendedor;
import com.pedropareschi.cooperativacredito.dto.VendedorDTO;
import com.pedropareschi.cooperativacredito.services.VendedorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vendedores")
public class VendedorController {
    private final VendedorService vendedorService;

    @Autowired
    public VendedorController(VendedorService vendedorService) {
        this.vendedorService = vendedorService;
    }

    @GetMapping
    public ResponseEntity<List<Vendedor>> getAllVendedores() {
        List<Vendedor> vendedores = vendedorService.getAllVendedores();
        return ResponseEntity.ok(vendedores);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vendedor> getVendedorById(@PathVariable Long id) {
        Vendedor vendedor = vendedorService.getVendedorById(id);
        return ResponseEntity.ok(vendedor);
    }

    @PostMapping
    public ResponseEntity<Vendedor> createVendedor(@Valid @RequestBody VendedorDTO vendedorDTO) {
        Vendedor vendedor = convertDtoToEntity(vendedorDTO);

        Vendedor createdVendedor = vendedorService.createVendedor(vendedor);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdVendedor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vendedor> updateVendedor(@PathVariable Long id, @Valid @RequestBody VendedorDTO vendedorDTO) {
        Vendedor vendedor = convertDtoToEntity(vendedorDTO);
        vendedor.setId(id);

        Vendedor updatedVendedor = vendedorService.updateVendedor(vendedor);
        return ResponseEntity.ok(updatedVendedor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVendedor(@PathVariable Long id) {
        vendedorService.deleteVendedor(id);
        return ResponseEntity.noContent().build();
    }

    private Vendedor convertDtoToEntity(VendedorDTO vendedorDTO) {
        Vendedor vendedor = new Vendedor();
        vendedor.setNome(vendedorDTO.getNome());
        vendedor.setCpf(vendedorDTO.getCpf());
        vendedor.setComissao(vendedorDTO.getComissao());

        return vendedor;
    }
}