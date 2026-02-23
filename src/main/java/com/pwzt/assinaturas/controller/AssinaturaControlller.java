package com.pwzt.assinaturas.controller;

import com.pwzt.assinaturas.infrastruct.dto.RequisicaoAssinaturaDTO;
import com.pwzt.assinaturas.infrastruct.dto.RespostaAssinaturaDTO;
import com.pwzt.assinaturas.infrastruct.entity.Assinatura;
import com.pwzt.assinaturas.service.AssinaturaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor

@RequestMapping("/subscriptions")
@RestController

public class AssinaturaControlller {

    private final AssinaturaService assinaturaService;

    @PostMapping
    public ResponseEntity<RespostaAssinaturaDTO> solicitarAssinatura(@RequestBody @Valid RequisicaoAssinaturaDTO requisicaoAssinaturaDto){
        RespostaAssinaturaDTO respostaAssinaturaDto = assinaturaService.solicitarAssinatura(requisicaoAssinaturaDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(respostaAssinaturaDto);
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<Void> cancelarAssinatura(@PathVariable UUID id){
        assinaturaService.cancelarAssinatura(id);
        return ResponseEntity.ok().build();
    }

}
