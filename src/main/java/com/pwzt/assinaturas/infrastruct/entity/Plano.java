package com.pwzt.assinaturas.infrastruct.entity;

import com.pwzt.assinaturas.infrastruct.enumerator.CicloCobranca;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


import java.math.BigDecimal;
import java.util.UUID;

@Table(name = "planos")
@Entity

@Setter
@Getter

public class Plano {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank
    private String nome;

    @NotNull
    private BigDecimal valor;

    @Enumerated(EnumType.STRING)
    private CicloCobranca cicloCobranca;

}
