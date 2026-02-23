package com.pwzt.assinaturas.scheduler;

import com.pwzt.assinaturas.infrastruct.entity.Assinatura;
import com.pwzt.assinaturas.infrastruct.enumerator.Status;
import com.pwzt.assinaturas.infrastruct.repository.AssinaturaRepository;
import com.pwzt.assinaturas.infrastruct.repository.EventoRepository;
import com.pwzt.assinaturas.infrastruct.repository.PlanoRepository;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class VerificadorAssinaturas {

    private final AssinaturaRepository assinaturaRepository;

    public VerificadorAssinaturas(AssinaturaRepository assinaturaRepository){
        this.assinaturaRepository = assinaturaRepository;
    }

    @Scheduled(cron = "0 0 1 * * *")
    @Transactional
    public void rotinaDeSuspencao(){
        List<Assinatura> expiradas = assinaturaRepository
                .findByDataProximaCobrancaBeforeAndStatus(LocalDate.now(), Status.ATIVA);

        expiradas.stream().forEach(assinatura -> assinatura.setStatus(Status.SUSPENSA));

        assinaturaRepository.saveAll(expiradas);
    }

}
