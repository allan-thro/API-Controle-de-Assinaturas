package com.pwzt.assinaturas.infrastruct.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record ReportDTO(
        @JsonProperty("total_active") Long inscricoesAtivas,
        @JsonProperty("total_cancelled") Long inscricoesCanceladas,
        @JsonProperty("plans") List<PlanoReportDTO> listaPlanos
) {}
