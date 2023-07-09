package com.pedropareschi.cooperativacredito.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BemDuravel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private double valor;
    @OneToOne(mappedBy = "bemDuravel")
    private Contrato contrato;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BemDuravel)) return false;
        BemDuravel that = (BemDuravel) o;
        return Double.compare(that.valor, valor) == 0 && Objects.equals(id, that.id) && Objects.equals(nome, that.nome) && Objects.equals(contrato, that.contrato);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, valor, contrato);
    }
}
