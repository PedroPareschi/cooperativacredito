package com.pedropareschi.cooperativacredito.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SituacaoPagamento {
    PENDENTE(0),
    ATRASADO(1),
    PAGO(2);

    private int cod;

    public static SituacaoPagamento fromCod(int cod) {
        for (SituacaoPagamento situation : SituacaoPagamento.values()) {
            if (cod == situation.getCod()) {
                return situation;
            }
        }
        return null;
    }
}

