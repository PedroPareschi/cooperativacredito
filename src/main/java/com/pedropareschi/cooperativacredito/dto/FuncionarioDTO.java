package com.pedropareschi.cooperativacredito.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FuncionarioDTO {
    private String nome;
    private String cpf;
    private double salario;
    private boolean temNomeLimpo;
    private Long empresaId;
}