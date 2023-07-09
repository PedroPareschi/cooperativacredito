package com.pedropareschi.cooperativacredito.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContratoDTO {
    @NotNull
    private double taxaDeJuros;
    @NotNull
    private int mesesParcelamento;
    @NotNull
    private Long funcionarioId;
    @NotNull
    private Long vendedorId;
    @NotNull
    private BemDuravelDTO bemDuravel;
}