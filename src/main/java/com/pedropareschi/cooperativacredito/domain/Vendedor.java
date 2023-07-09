package com.pedropareschi.cooperativacredito.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

import java.util.List;
import java.util.Objects;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "cpf"))
public class Vendedor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String nome;
    @CPF
    @NotBlank
    private String cpf;
    @NotNull
    private double comissao;
    @OneToMany(mappedBy = "vendedor")
    @JsonIgnore
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
