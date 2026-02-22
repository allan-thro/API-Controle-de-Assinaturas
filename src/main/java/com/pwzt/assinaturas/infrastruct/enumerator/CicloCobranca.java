package com.pwzt.assinaturas.infrastruct.enumerator;

import lombok.Getter;

@Getter

public enum CicloCobranca {
    SEMESTRAL(6, "semestral"),
    MENSAL(1, "mensal"),
    ANUAL(12, "anual");

    private final String descricao;
    private final int meses;

    CicloCobranca(int meses, String descricao){
        this.descricao = descricao;
        this.meses = meses;
    }

}

