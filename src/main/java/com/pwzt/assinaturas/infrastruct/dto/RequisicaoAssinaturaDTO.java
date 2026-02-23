package com.pwzt.assinaturas.infrastruct.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import java.util.UUID;

public record RequisicaoAssinaturaDTO(
        @JsonAlias("plan_id") UUID planoId,
        @JsonAlias("customer_email") String emailCliente
) {}
