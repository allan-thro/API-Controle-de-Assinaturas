package com.pwzt.assinaturas.service;

import com.pwzt.assinaturas.infrastruct.dto.PlanoReportDTO;
import com.pwzt.assinaturas.infrastruct.dto.RespostaReportDTO;
import com.pwzt.assinaturas.infrastruct.entity.Plano;
import com.pwzt.assinaturas.infrastruct.enumerator.Status;
import com.pwzt.assinaturas.infrastruct.repository.AssinaturaRepository;
import com.pwzt.assinaturas.infrastruct.repository.PlanoRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReportService {

    private final AssinaturaRepository assinaturaRepository;
    private final PlanoRepository planoRepository;

    public ReportService(AssinaturaRepository assinaturaRepository, PlanoRepository planoRepository){
        this.assinaturaRepository = assinaturaRepository;
        this.planoRepository = planoRepository;
    }

    public RespostaReportDTO obterMetricas(){
        long totalCanceladas = assinaturaRepository.countByStatus(Status.CANCELADA);
        long totalAtivas = assinaturaRepository.countByStatus(Status.ATIVA);

        List<PlanoReportDTO> listaPlanoReport = new ArrayList<>();
        List<Plano> listaPlanos = planoRepository.findAll();

        for(Plano plano : listaPlanos){
            long ativas = assinaturaRepository.countByPlanoIdAndStatus(plano.getId(), Status.ATIVA);

            PlanoReportDTO planoRepot = new PlanoReportDTO(
                    plano.getId(),
                    plano.getNome(),
                    ativas
            );

            listaPlanoReport.add(planoRepot);
        }

        return new RespostaReportDTO(totalAtivas, totalCanceladas, listaPlanoReport);
    }

}
