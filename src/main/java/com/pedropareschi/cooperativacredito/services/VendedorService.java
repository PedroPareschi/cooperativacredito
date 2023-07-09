package com.pedropareschi.cooperativacredito.services;

import com.pedropareschi.cooperativacredito.domain.Vendedor;
import com.pedropareschi.cooperativacredito.exceptions.ResourceNotFoundException;
import com.pedropareschi.cooperativacredito.repositories.VendedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VendedorService {
    private final VendedorRepository vendedorRepository;

    @Autowired
    public VendedorService(VendedorRepository vendedorRepository) {
        this.vendedorRepository = vendedorRepository;
    }

    public List<Vendedor> getAllVendedores() {
        return vendedorRepository.findAll();
    }

    public Vendedor getVendedorById(Long id) {
        return vendedorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vendedor não encontrado com id: " + id));
    }

    public Vendedor createVendedor(Vendedor vendedor) {
        return vendedorRepository.save(vendedor);
    }

    public Vendedor updateVendedor(Vendedor vendedor) {
        if (!vendedorRepository.existsById(vendedor.getId())) {
            throw new ResourceNotFoundException("Vendedor não encontrado com id: " + vendedor.getId());
        }
        return vendedorRepository.save(vendedor);
    }

    public void deleteVendedor(Long id) {
        if (!vendedorRepository.existsById(id)) {
            throw new ResourceNotFoundException("Vendedor não encontrado com id: " + id);
        }
        vendedorRepository.deleteById(id);
    }
}