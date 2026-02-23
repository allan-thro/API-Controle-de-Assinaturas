package com.pwzt.assinaturas.controller;

import com.pwzt.assinaturas.infrastruct.dto.RespostaReportDTO;
import com.pwzt.assinaturas.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor

@RequestMapping("/reports/subscriptions")
@RestController

public class ReportController {

    private final ReportService reportService;

    @GetMapping
    public ResponseEntity<RespostaReportDTO> obterMetricas(){
        RespostaReportDTO respostaReportDto = reportService.obterMetricas();
        return ResponseEntity.ok().body(respostaReportDto);
    }

}
