package com.pwzt.assinaturas.infrastruct.entity;

import com.pwzt.assinaturas.infrastruct.dto.DadosEventoDTO;
import com.pwzt.assinaturas.infrastruct.enumerator.TipoEvento;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Table(name = "eventos")
@Entity

@Setter
@Getter

public class Evento {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    private TipoEvento tipoEvento;

    @NotBlank
    private DadosEventoDTO dadosEvento;

    private Boolean processado;

    public Evento(TipoEvento tipoEvento, DadosEventoDTO dadosEvento, Boolean processado){
        this.tipoEvento = tipoEvento;
        this.dadosEvento = dadosEvento;
        this.processado = processado;
    }

}
