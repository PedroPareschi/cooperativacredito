package com.pedropareschi.cooperativacredito.repositories;

import com.pedropareschi.cooperativacredito.domain.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
}
