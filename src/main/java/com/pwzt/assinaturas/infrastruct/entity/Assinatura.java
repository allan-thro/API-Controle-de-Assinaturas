package com.pwzt.assinaturas.infrastruct.entity;

import com.pwzt.assinaturas.infrastruct.dto.RequisicaoAssinaturaDTO;
import com.pwzt.assinaturas.infrastruct.enumerator.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Table(name = "assinaturas")
@Entity

@Getter
@Setter

public class Assinatura {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull
    private UUID planoId;

    @NotNull
    private String emailCliente;

    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDate dataProximaCobranca;

    public Assinatura(RequisicaoAssinaturaDTO dto, Status status, LocalDate data){
        this.status = status;
        this.emailCliente = dto.emailCliente();
        this.dataProximaCobranca = data;
        this.planoId = dto.planoId();
    }

}
