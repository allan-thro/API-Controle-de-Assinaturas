package com.pwzt.assinaturas.infrastruct.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pwzt.assinaturas.infrastruct.enumerator.Status;

import java.time.LocalDate;
import java.util.UUID;

public record RespostaAssinaturaDTO(
        @JsonProperty("subscription_id") UUID assinaturaId,
        @JsonProperty("status") Status status,
        @JsonProperty("next_billing_date") LocalDate dataProximaCobranca
) {}
