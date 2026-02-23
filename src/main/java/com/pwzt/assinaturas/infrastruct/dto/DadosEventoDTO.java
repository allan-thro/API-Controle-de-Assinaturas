package com.pwzt.assinaturas.infrastruct.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record DadosEventoDTO(
        @JsonAlias("subscription_id") UUID assinaturaId,
        UUID planoId,
        @JsonAlias("amount") BigDecimal valor,
        @JsonAlias("date") LocalDate dataAtual
){}