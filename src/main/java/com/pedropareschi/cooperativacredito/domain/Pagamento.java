package com.pedropareschi.cooperativacredito.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Pagamento {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_contrato")
    private Contrato contrato;
    @NotNull
    private Date mesCobranca;
    private Date mesPagamento;
}
