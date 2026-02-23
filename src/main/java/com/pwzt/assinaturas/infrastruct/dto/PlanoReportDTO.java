package com.pwzt.assinaturas.infrastruct.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public record PlanoReportDTO(
   @JsonProperty("plan_id") UUID planoId,
   @JsonProperty("name") String nome,
   @JsonProperty("active_subscriptions") Long incricoesAtivas
) {}
