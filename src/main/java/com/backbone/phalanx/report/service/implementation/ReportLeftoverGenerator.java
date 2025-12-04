package com.backbone.phalanx.report.service.implementation;

import com.backbone.phalanx.product.dto.ProductResponseDto;
import com.backbone.phalanx.product.service.ProductService;
import com.backbone.phalanx.report.model.ReportType;
import com.backbone.phalanx.report.service.ReportGenerator;
import com.lowagie.text.pdf.BaseFont;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReportLeftoverGenerator implements ReportGenerator {

    private final ProductService productService;

    @Override
    public ReportType getReportType() {
        return ReportType.LEFTOVERS;
    }

    @Override
    public byte[] generateReport() throws IOException {
        return generateReportLeftOverPdf(productService.getAllProducts());
    }

    private byte[] generateReportLeftOverPdf(List<ProductResponseDto> products) throws IOException {
        String html = parseThymeleafTemplate(products);
        return generatePdfFromHtml(html);
    }

    private String parseThymeleafTemplate(List<ProductResponseDto> products) {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCharacterEncoding("UTF-8");
        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

        Context context = new Context();
        context.setVariable("products", products);

        return templateEngine.process("report-leftover.html", context);
    }

    private byte[] generatePdfFromHtml(String html) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ITextRenderer renderer = new ITextRenderer();

        String fontPath = Objects.requireNonNull(
                getClass().getResource("/fonts/DejaVuSans.ttf")
        ).getPath();

        renderer.getFontResolver().addFont(
                fontPath,
                BaseFont.IDENTITY_H,
                BaseFont.EMBEDDED
        );

        renderer.getSharedContext().setReplacedElementFactory(
                renderer.getSharedContext().getReplacedElementFactory()
        );
        renderer.getSharedContext().getTextRenderer().setSmoothingThreshold(0);

        renderer.setDocumentFromString(html);
        renderer.layout();
        renderer.createPDF(outputStream);
        return outputStream.toByteArray();
    }
}
