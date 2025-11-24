package com.backbone.phalanx.report.service.implementation;

import com.backbone.phalanx.product.dto.ProductResponseDto;
import com.backbone.phalanx.product.service.ProductService;
import com.backbone.phalanx.report.service.ReportLeftoverPdfService;
import com.backbone.phalanx.report.service.ReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReportServiceImpl implements ReportService {

    private final ProductService productService;
    private final ReportLeftoverPdfService reportLeftoverPdfService;

    @Override
    public byte[] getLeftoverReport() {

        List<ProductResponseDto> products = productService.getAllProducts();

        return reportLeftoverPdfService.generateReportLeftOverPdf(products);
    }

    @Override
    public byte[] getTransactionsPdf() {
        return new byte[0];
    }
}
