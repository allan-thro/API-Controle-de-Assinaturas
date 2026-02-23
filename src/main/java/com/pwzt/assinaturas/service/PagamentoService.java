package com.pwzt.assinaturas.service;

import com.pwzt.assinaturas.infrastruct.dto.DadosEventoDTO;
import com.pwzt.assinaturas.infrastruct.dto.RequisicaoPagamentoDTO;
import com.pwzt.assinaturas.infrastruct.entity.Assinatura;
import com.pwzt.assinaturas.infrastruct.entity.Evento;
import com.pwzt.assinaturas.infrastruct.repository.AssinaturaRepository;
import com.pwzt.assinaturas.infrastruct.repository.EventoRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

@Service

public class PagamentoService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private final EventoRepository eventoRepository;
    private final AssinaturaRepository assinaturaRepository;

    public PagamentoService(EventoRepository eventoRepository, AssinaturaRepository assinaturaRepository){
        this.eventoRepository = eventoRepository;
        this.assinaturaRepository = assinaturaRepository;
    }

    @TransactionalEventListener
    public void receberPagamento(RequisicaoPagamentoDTO requisicaoPagamentoDto){
        Assinatura assinaturaPaga = assinaturaRepository.findById(requisicaoPagamentoDto.assinaturaId()).orElseThrow();

        DadosEventoDTO dadosEventoDto = new DadosEventoDTO(
            requisicaoPagamentoDto.assinaturaId(),
            assinaturaPaga.getPlanoId(),
            requisicaoPagamentoDto.valor(),
            requisicaoPagamentoDto.dataAtual()
        );

        Evento mensagemEvento = new Evento(
            requisicaoPagamentoDto.tipoEvento(),
            dadosEventoDto,
            false
        );

        eventoRepository.save(mensagemEvento);
        rabbitTemplate.convertAndSend("fila_eventos", mensagemEvento);
    }

}
