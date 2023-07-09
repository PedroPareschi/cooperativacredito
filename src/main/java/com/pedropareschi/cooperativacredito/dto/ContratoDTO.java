package com.pedropareschi.cooperativacredito.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ContratoDTO {
    @NotNull
    private double taxaDeJuros;
    @NotNull
    private Date prazoFinal;
    @NotNull
    private Long funcionarioId;
    @NotNull
    private Long vendedorId;
    @NotNull
    private BemDuravelDTO bemDuravel;
}