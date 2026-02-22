package com.pwzt.assinaturas.infrastruct.enumerator;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TipoEvento {
    INSCRICAO_CRIADA ("subscription_created"),
    PAGAMENTO_SUCEDIDO ("payment_success"),
    PAGAMENTO_FALHO ("payment_failed"),
    INSCRICAO_CANCELADA ("subscription_canceled");

    private final String valorJson;

    TipoEvento(String valorJson){
        this.valorJson = valorJson;
    }

    @JsonValue
    public String getValorJson() {
        return valorJson;
    }
}
