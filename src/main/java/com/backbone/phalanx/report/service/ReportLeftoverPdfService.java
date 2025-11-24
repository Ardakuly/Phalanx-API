package com.backbone.phalanx.report.service;

import com.backbone.phalanx.product.dto.ProductResponseDto;

import java.util.List;

public interface ReportLeftoverPdfService {

    /**
     * Generates a PDF document containing product details based on the provided list of products.
     *
     * @param products a list of ProductResponseDto objects containing details about the products
     * @return a byte array representing the generated PDF document
     */
    byte[] generateReportLeftOverPdf(List<ProductResponseDto> products);
}
