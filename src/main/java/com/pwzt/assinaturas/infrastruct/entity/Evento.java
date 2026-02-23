package com.pwzt.assinaturas.infrastruct.entity;

import com.pwzt.assinaturas.infrastruct.dto.DadosEventoDTO;
import com.pwzt.assinaturas.infrastruct.enumerator.TipoEvento;
import com.pwzt.assinaturas.infrastruct.utility.ConversorDadosEvento;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Table(name = "eventos")
@Entity

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter

public class Evento {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    private TipoEvento tipoEvento;

    @Convert(converter = ConversorDadosEvento.class)
    @Column(columnDefinition = "TEXT")
    private DadosEventoDTO dadosEvento;

    private Boolean processado;

    public Evento(TipoEvento tipoEvento, DadosEventoDTO dadosEvento, Boolean processado){
        this.tipoEvento = tipoEvento;
        this.dadosEvento = dadosEvento;
        this.processado = processado;
    }

}
