package com.pedropareschi.cooperativacredito.repositories;

import com.pedropareschi.cooperativacredito.domain.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {

    @Modifying
    @Query("DELETE FROM Pagamento p WHERE p.contrato.id = :idContrato")
    void deleteAllByContratoId(Long idContrato);

    @Query("SELECT p FROM Pagamento p WHERE p.contrato.id = :idContrato AND p.posicao = :posicao")
    Pagamento getPagamentoByPosicaoAndContrato(int posicao, Long idContrato);

    @Query("SELECT p FROM Pagamento p where p.contrato.id = :idContrato")
    List<Pagamento> getPagamentosByContrato(Long idContrato);
}
