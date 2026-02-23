package com.pwzt.assinaturas.service;

import com.pwzt.assinaturas.infrastruct.dto.DadosEventoDTO;
import com.pwzt.assinaturas.infrastruct.dto.RequisicaoAssinaturaDTO;
import com.pwzt.assinaturas.infrastruct.dto.RespostaAssinaturaDTO;
import com.pwzt.assinaturas.infrastruct.entity.Assinatura;
import com.pwzt.assinaturas.infrastruct.entity.Evento;
import com.pwzt.assinaturas.infrastruct.entity.Plano;
import com.pwzt.assinaturas.infrastruct.enumerator.Status;
import com.pwzt.assinaturas.infrastruct.enumerator.TipoEvento;
import com.pwzt.assinaturas.infrastruct.repository.AssinaturaRepository;
import com.pwzt.assinaturas.infrastruct.repository.EventoRepository;
import com.pwzt.assinaturas.infrastruct.repository.PlanoRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Service

public class AssinaturaService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private final AssinaturaRepository assinaturaRepository;
    private final EventoRepository eventoRepository;
    private final PlanoRepository planoRepository;

    public AssinaturaService(PlanoRepository planoRepository, AssinaturaRepository assinaturaRepository, EventoRepository eventoRepository){
        this.assinaturaRepository = assinaturaRepository;
        this.eventoRepository = eventoRepository;
        this.planoRepository = planoRepository;
    }

    @Transactional
    public RespostaAssinaturaDTO solicitarAssinatura(RequisicaoAssinaturaDTO assinaturaDto){
        Optional<Plano> plano = planoRepository.findById(assinaturaDto.planoId());
        if(plano.isEmpty()) throw new EntityNotFoundException(); // exception

        int proximaCobrancaMeses = plano.get().getCicloCobranca().getMeses();

        Assinatura assinatura = new Assinatura(assinaturaDto, Status.PENDENTE,
                LocalDate.now().plusMonths(proximaCobrancaMeses));
        assinaturaRepository.save(assinatura);

        DadosEventoDTO dadosEventoDto = new DadosEventoDTO(
                assinatura.getId(),
                plano.get().getId(),
                plano.get().getValor(),
                LocalDate.now()
        );

        Evento mensagemEvento = new Evento(
                TipoEvento.INSCRICAO_CRIADA,
                dadosEventoDto,
                false
        );

        eventoRepository.save(mensagemEvento);
        rabbitTemplate.convertAndSend("fila_eventos", mensagemEvento);

        return new RespostaAssinaturaDTO(
                assinatura.getId(),
                assinatura.getStatus(),
                assinatura.getDataProximaCobranca()
        );
    }

    @Transactional
    public void cancelarAssinatura(UUID id){
        Assinatura assinatura = assinaturaRepository.findById(id).orElseThrow();

        assinatura.setStatus(Status.CANCELADA);
        assinaturaRepository.save(assinatura);

        DadosEventoDTO dadosEventoDto = new DadosEventoDTO(
                assinatura.getId(),
                null,
                null,
                LocalDate.now()
        );

        Evento mensagemEvento = new Evento(
                TipoEvento.INSCRICAO_CANCELADA,
                dadosEventoDto,
                false
        );

        eventoRepository.save(mensagemEvento);
        rabbitTemplate.convertAndSend("fila_eventos", mensagemEvento);
    }

}
