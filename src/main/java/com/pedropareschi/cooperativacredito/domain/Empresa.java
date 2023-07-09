package com.pedropareschi.cooperativacredito.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CNPJ;

import java.util.List;
import java.util.Objects;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Empresa {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    @NotBlank
    private String nome;
    @CNPJ
    @NotBlank
    private String cnpj;
    @NotNull
    private double comissao;
    @OneToMany(mappedBy = "empresa")
    private List<Funcionario> funcionarios;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Empresa)) return false;
        Empresa empresa = (Empresa) o;
        return Double.compare(empresa.comissao, comissao) == 0 && Objects.equals(id, empresa.id) && Objects.equals(nome, empresa.nome) && Objects.equals(cnpj, empresa.cnpj) && Objects.equals(funcionarios, empresa.funcionarios);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, cnpj, comissao, funcionarios);
    }
}
