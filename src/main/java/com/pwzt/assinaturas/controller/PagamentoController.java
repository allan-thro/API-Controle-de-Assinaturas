package com.pwzt.assinaturas.controller;

import com.pwzt.assinaturas.infrastruct.dto.RequisicaoPagamentoDTO;
import com.pwzt.assinaturas.service.PagamentoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor

@RequestMapping("/webhooks/payment")
@RestController

public class PagamentoController {

    private final PagamentoService pagamentoService;

    @PostMapping
    public ResponseEntity<Void> realizarPagamento(@RequestBody @Valid RequisicaoPagamentoDTO requisicaoPagamentoDto){
        pagamentoService.receberPagamento(requisicaoPagamentoDto);
        return ResponseEntity.ok().build();
    }

}
