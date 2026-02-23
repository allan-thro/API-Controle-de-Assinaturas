package com.pwzt.assinaturas.scheduler;

import com.pwzt.assinaturas.infrastruct.dto.DadosEventoDTO;
import com.pwzt.assinaturas.infrastruct.entity.Assinatura;
import com.pwzt.assinaturas.infrastruct.entity.Evento;
import com.pwzt.assinaturas.infrastruct.enumerator.Status;
import com.pwzt.assinaturas.infrastruct.enumerator.TipoEvento;
import com.pwzt.assinaturas.infrastruct.repository.AssinaturaRepository;
import com.pwzt.assinaturas.infrastruct.repository.EventoRepository;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Component
public class VerificadorAssinaturas {

    private final AssinaturaRepository assinaturaRepository;
    private final EventoRepository eventoRepository;

    public VerificadorAssinaturas(AssinaturaRepository assinaturaRepository, EventoRepository eventoRepository){
        this.assinaturaRepository = assinaturaRepository;
        this.eventoRepository = eventoRepository;
    }

    @Scheduled(cron = "0 0 1 * * *")
    @Transactional
    public void rotinaDeSuspencao(){
        List<Assinatura> expiradas = assinaturaRepository
                .findByDataProximaCobrancaBeforeAndStatus(LocalDate.now(), Status.ATIVA);

        for(Assinatura assinatura : expiradas){
            assinatura.setStatus(Status.SUSPENSA);

            DadosEventoDTO dadosEvento = new DadosEventoDTO(
                    assinatura.getId(),
                    assinatura.getPlanoId(),
                    BigDecimal.ZERO,
                    LocalDate.now()
            );

            Evento evento = new Evento(TipoEvento.INSCRICAO_SUSPENSA, dadosEvento, false);
            eventoRepository.save(evento);
        }

        assinaturaRepository.saveAll(expiradas);
    }

}
