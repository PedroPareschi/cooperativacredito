package com.pedropareschi.cooperativacredito.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Vendedor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private double comissao;
    @OneToMany(mappedBy = "vendedor")
    private List<Contrato> contratos;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vendedor)) return false;
        Vendedor vendedor = (Vendedor) o;
        return Double.compare(vendedor.comissao, comissao) == 0 && Objects.equals(id, vendedor.id) && Objects.equals(contratos, vendedor.contratos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, comissao, contratos);
    }
}
