package com.pedropareschi.cooperativacredito.repositories;

import com.pedropareschi.cooperativacredito.domain.BemDuravel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BemDuravelRepository extends JpaRepository<BemDuravel, Long> {
}
