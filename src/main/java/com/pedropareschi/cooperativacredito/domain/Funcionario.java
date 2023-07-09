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
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "cpf"))
public class Funcionario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String nome;
    @CPF
    @NotBlank
    private String cpf;
    @NotNull
    private double salario;
    private double totalDevido;
    @NotNull
    private boolean temNomeLimpo;
    @ManyToOne
    @JoinColumn(name = "id_empresa")
    private Empresa empresa;
    @OneToMany(mappedBy = "funcionario")
    @JsonIgnore
    private List<Contrato> contratos;

    public double getMargemConsignada() {
        return this.salario * 0.3;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Funcionario)) return false;
        Funcionario that = (Funcionario) o;
        return Double.compare(that.salario, salario) == 0 && temNomeLimpo == that.temNomeLimpo && Objects.equals(id, that.id) && Objects.equals(nome, that.nome) && Objects.equals(cpf, that.cpf) && Objects.equals(empresa, that.empresa) && Objects.equals(contratos, that.contratos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, cpf, salario, temNomeLimpo, empresa, contratos);
    }
}
