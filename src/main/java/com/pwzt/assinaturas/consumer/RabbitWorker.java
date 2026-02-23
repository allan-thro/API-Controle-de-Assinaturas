package com.pwzt.assinaturas.consumer;

import com.pwzt.assinaturas.infrastruct.dto.DadosEventoDTO;
import com.pwzt.assinaturas.infrastruct.entity.Assinatura;
import com.pwzt.assinaturas.infrastruct.entity.Evento;
import com.pwzt.assinaturas.infrastruct.entity.Plano;
import com.pwzt.assinaturas.infrastruct.enumerator.Status;
import com.pwzt.assinaturas.infrastruct.enumerator.TipoEvento;
import com.pwzt.assinaturas.infrastruct.repository.AssinaturaRepository;
import com.pwzt.assinaturas.infrastruct.repository.EventoRepository;
import com.pwzt.assinaturas.infrastruct.repository.PlanoRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import java.time.LocalDate;

@Component
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
    @TransactionalEventListener
    public void manejadorEventos(Evento eventoDaFila){
        DadosEventoDTO dadosEvento = eventoDaFila.getDadosEvento();
        int proximaCobrancaMeses;
        LocalDate cobrancaSalva;
        Plano planoAssinado;

        Assinatura assinatura = assinaturaRepository.findById(dadosEvento.assinaturaId()).orElseThrow();
        Evento eventoDoBanco = eventoRepository.findById(eventoDaFila.getId()).orElseThrow();
        cobrancaSalva = assinatura.getDataProximaCobranca();

        if(eventoDoBanco.getProcessado()) return; // evitar cobranças duplas caso chamadas duplicadas (erro interno)

        switch(eventoDaFila.getTipoEvento()){
            case TipoEvento.INSCRICAO_CRIADA:
                assinatura.setStatus(Status.ATIVA);
                assinaturaRepository.save(assinatura);
                break;
            case TipoEvento.PAGAMENTO_SUCEDIDO:
                planoAssinado = planoRepository.findById(dadosEvento.planoId()).orElseThrow();
                proximaCobrancaMeses = planoAssinado.getCicloCobranca().getMeses();

                if(assinatura.getStatus().equals(Status.SUSPENSA)){
                    assinatura.setDataProximaCobranca(dadosEvento.dataAtual().plusMonths(proximaCobrancaMeses));
                }
                else{
                    assinatura.setDataProximaCobranca(cobrancaSalva.plusMonths(proximaCobrancaMeses));
                }

                assinatura.setStatus(Status.ATIVA);

                assinaturaRepository.save(assinatura);
                break;
            case TipoEvento.PAGAMENTO_FALHO:
                /*
                    Não alterar nada (Mandar email de notificação)
                */
                break;
            case TipoEvento.INSCRICAO_CANCELADA:
                /*
                    Não alterar nada (Mandar email de despedida)
                */
                break;
        }

        eventoDoBanco.setProcessado(true);
        eventoRepository.save(eventoDoBanco);
    }

}
