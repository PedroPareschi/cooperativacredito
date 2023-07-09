package com.pedropareschi.cooperativacredito.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class VendedorDTO {
    private String nome;
    private String cpf;
    private double comissao;

}