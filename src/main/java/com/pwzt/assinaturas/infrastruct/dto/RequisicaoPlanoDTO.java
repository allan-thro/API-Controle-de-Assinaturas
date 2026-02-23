package com.pwzt.assinaturas.infrastruct.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.pwzt.assinaturas.infrastruct.enumerator.CicloCobranca;

import java.math.BigDecimal;

public record RequisicaoPlanoDTO(
        @JsonAlias("name") String nome,
        @JsonAlias("price")BigDecimal valor,
        @JsonAlias("billing_cicle")CicloCobranca cicloCobranca
) {}
