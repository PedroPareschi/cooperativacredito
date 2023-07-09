package com.pedropareschi.cooperativacredito.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Contrato {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private double taxaDeJuros;
    @NotNull
    private Date prazoFinal;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_funcionario")
    private Funcionario funcionario;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_vendedor")
    private Vendedor vendedor;
    @OneToMany(mappedBy = "contrato")
    private List<Pagamento> pagamentos;
    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    private BemDuravel bemDuravel;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Contrato)) return false;
        Contrato contrato = (Contrato) o;
        return Double.compare(contrato.taxaDeJuros, taxaDeJuros) == 0 && Objects.equals(id, contrato.id) && Objects.equals(prazoFinal, contrato.prazoFinal) && Objects.equals(funcionario, contrato.funcionario) && Objects.equals(vendedor, contrato.vendedor) && Objects.equals(pagamentos, contrato.pagamentos) && Objects.equals(bemDuravel, contrato.bemDuravel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, taxaDeJuros, prazoFinal, funcionario, vendedor, pagamentos, bemDuravel);
    }
}
