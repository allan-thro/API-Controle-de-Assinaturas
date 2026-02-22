package com.pwzt.assinaturas.consumer;

import com.pwzt.assinaturas.infrastruct.dto.DadosEventoDTO;
import com.pwzt.assinaturas.infrastruct.entity.Evento;
import com.pwzt.assinaturas.infrastruct.enumerator.TipoEvento;
import com.pwzt.assinaturas.infrastruct.repository.AssinaturaRepository;
import com.pwzt.assinaturas.infrastruct.repository.EventoRepository;
import com.pwzt.assinaturas.infrastruct.repository.PlanoRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

public class RabbitWorker {

    private final AssinaturaRepository assinaturaRepository;
    private final EventoRepository eventoRepository;
    private final PlanoRepository planoRepository;

    public RabbitWorker(PlanoRepository planoRepository, AssinaturaRepository assinaturaRepository, EventoRepository eventoRepository){
        this.assinaturaRepository = assinaturaRepository;
        this.eventoRepository = eventoRepository;
        this.planoRepository = planoRepository;
    }

    @RabbitListener(queues = "fila_eventos")
    public void manejadorEventos(Evento evento){
        DadosEventoDTO dadosEvento = evento.getDadosEvento();

        switch(evento.getTipoEvento()){
            case TipoEvento.INSCRICAO_CRIADA:
                /*
                    Confirmar a inscrição
                    -> ir no banco assinatura setar como ativa
                    -> setar proxima cobrança para proxima data
                */
                break;
            case TipoEvento.PAGAMENTO_SUCEDIDO:
                /*
                    Renovar incrição
                    -> ir no banco assinatura setar como ativa (caso esteja suspensa)
                    -> setar proxima cobrança para proxima data
                */
                break;
            case TipoEvento.PAGAMENTO_FALHO:
                /*
                    Não alterar nada
                    -> ir no banco assinatura setar como ativa (caso esteja suspensa)
                    -> setar proxima cobrança para proxima data
                */
                break;
        }

        // atualizar evento como processado
    }

}
