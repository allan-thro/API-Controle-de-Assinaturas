package com.pwzt.assinaturas.service;

import com.pwzt.assinaturas.infrastruct.dto.RequisicaoPlanoDTO;
import com.pwzt.assinaturas.infrastruct.entity.Plano;
import com.pwzt.assinaturas.infrastruct.repository.PlanoRepository;
import org.springframework.stereotype.Service;

@Service

public class PlanoService {

    private final PlanoRepository planoRepository;

    public PlanoService(PlanoRepository planoRepository){
        this.planoRepository = planoRepository;
    }

    public void cadastrarPlano(RequisicaoPlanoDTO requisicaoPlano){
        Plano plano = new Plano(requisicaoPlano);
        planoRepository.saveAndFlush(plano);
    }

}
