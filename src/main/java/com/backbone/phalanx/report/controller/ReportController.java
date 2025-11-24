package com.backbone.phalanx.report.controller;

import com.backbone.phalanx.report.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/report")
@Tag(name = "Report", description = "Generation of reports based on data in database")
@RequiredArgsConstructor
@Slf4j
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/leftover")
    @Operation(
            summary = "Get report regarding leftovers in stock",
            description = "Return a pdf in byte array representative"
    )
    public ResponseEntity<byte[]> getReportLeftoverPdf() {
        return new ResponseEntity<>(reportService.getLeftoverReport(), HttpStatus.OK);
    }

    @GetMapping("/transactions")
    @Operation(
            summary = "Get report regarding transactions",
            description = "Return a pdf in byte array representative"
    )
    public ResponseEntity<byte[]> getTransactionsPdf() {
        return new ResponseEntity<>(reportService.getTransactionsPdf(), HttpStatus.OK);
    }
}
