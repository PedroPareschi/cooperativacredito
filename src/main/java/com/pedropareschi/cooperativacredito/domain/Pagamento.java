package com.pedropareschi.cooperativacredito.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pedropareschi.cooperativacredito.domain.enums.SituacaoPagamento;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_contrato")
    @JsonIgnore
    private Contrato contrato;
    @NotNull
    private Date mesCobranca;
    @NotNull
    private int posicao;
    private SituacaoPagamento situacaoPagamento;

    public double getValor(){
        // MontanteAtual = Principal * (1 + TaxaDeJuros)^Tempo
        return contrato.getValorPrimeiraParcela() * Math.pow(1 + (contrato.getTaxaDeJuros()/100), posicao);
    }

    public double getComissaoVendedor(){
        if(posicao < 12){
            return getValor() * (contrato.getVendedor().getComissao() / 100);
        }
        return 0;
    }
    public double getComissaoEmpresa(){
        return getValor() * (contrato.getFuncionario().getEmpresa().getComissao() / 100);
    }
}
