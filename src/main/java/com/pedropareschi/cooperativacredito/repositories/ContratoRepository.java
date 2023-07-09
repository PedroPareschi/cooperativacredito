package com.pedropareschi.cooperativacredito.repositories;

import com.pedropareschi.cooperativacredito.domain.Contrato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContratoRepository extends JpaRepository<Contrato, Long> {
}
