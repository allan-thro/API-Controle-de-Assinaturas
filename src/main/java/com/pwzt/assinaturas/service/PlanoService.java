package com.pwzt.assinaturas.service;

import com.pwzt.assinaturas.infrastruct.entity.Plano;
import com.pwzt.assinaturas.infrastruct.repository.PlanoRepository;
import org.springframework.stereotype.Service;

@Service

public class PlanoService {

    private final PlanoRepository planoRepository;

    public PlanoService(PlanoRepository planoRepository){
        this.planoRepository = planoRepository;
    }

    public void cadastrarPlano(Plano plano){
        planoRepository.saveAndFlush(plano);
    }

}
