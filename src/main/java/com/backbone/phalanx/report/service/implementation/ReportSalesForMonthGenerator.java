package com.backbone.phalanx.report.service.implementation;

import com.backbone.phalanx.outbound_document.model.OutboundDocument;
import com.backbone.phalanx.outbound_document.service.OutboundDocumentService;
import com.backbone.phalanx.report.model.ReportType;
import com.backbone.phalanx.report.service.ReportGenerator;
import com.lowagie.text.pdf.BaseFont;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReportSalesForMonthGenerator implements ReportGenerator {

    private final OutboundDocumentService outboundDocumentService;

    @Override
    public ReportType getReportType() {
        return ReportType.SALES_FOR_MONTH;
    }

    @Override
    @Transactional
    public byte[] generateReport() throws IOException {
        return generateMonthlySalesReport(outboundDocumentService.getAllOutboundDocumentByInterval(
                LocalDateTime.now().minusMonths(1), LocalDateTime.now()
        ));
    }

    public byte[] generateMonthlySalesReport(List<OutboundDocument> outboundDocuments) throws IOException {
        String html = parseThymeleafTemplate(outboundDocuments);

        return generatePdfFromHtml(html);
    }

    private String parseThymeleafTemplate(List<OutboundDocument> transactions) {

        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCharacterEncoding("UTF-8");

        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

        double totalIncome = transactions.stream()
                .mapToDouble(OutboundDocument::getTotalIncome)
                .sum();

        double totalProfit = transactions.stream()
                .mapToDouble(OutboundDocument::getMargin)
                .sum();

        Context context = new Context();
        context.setVariable("transactions", transactions);
        context.setVariable("totalIncome", totalIncome);
        context.setVariable("totalProfit", totalProfit);

        return templateEngine.process("report-sales.html", context);
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

        renderer.getSharedContext().getTextRenderer().setSmoothingThreshold(0);

        renderer.setDocumentFromString(html);
        renderer.layout();
        renderer.createPDF(outputStream);

        return outputStream.toByteArray();
    }
}
