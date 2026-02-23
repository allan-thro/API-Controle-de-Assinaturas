package com.pwzt.assinaturas.infrastruct.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.pwzt.assinaturas.infrastruct.enumerator.TipoEvento;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record RequisicaoPagamentoDTO(
        @NotNull @JsonAlias("subscription_id") UUID assinaturaId,
        @NotNull @JsonAlias("event") TipoEvento tipoEvento,
        @NotNull @JsonAlias("amount") BigDecimal valor,
        @NotNull @JsonAlias("date")LocalDate dataAtual
) {}