package com.pedropareschi.cooperativacredito.repositories;

import com.pedropareschi.cooperativacredito.domain.Vendedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendedorRepository extends JpaRepository<Vendedor, Long> {
}
