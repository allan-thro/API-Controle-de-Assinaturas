package com.pwzt.assinaturas.service;

import com.pwzt.assinaturas.infrastruct.dto.DadosEventoDTO;
import com.pwzt.assinaturas.infrastruct.dto.RequisicaoPagamentoDTO;
import com.pwzt.assinaturas.infrastruct.entity.Evento;
import com.pwzt.assinaturas.infrastruct.repository.EventoRepository;
import jakarta.transaction.Transactional;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class PagamentoService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private final EventoRepository eventoRepository;

    public PagamentoService(EventoRepository eventoRepository){
        this.eventoRepository = eventoRepository;
    }

    @Transactional
    public void receberPagamento(RequisicaoPagamentoDTO requisicaoPagamentoDto){
        DadosEventoDTO dadosEventoDto = new DadosEventoDTO(
            requisicaoPagamentoDto.assinaturaId(),
            null,
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
