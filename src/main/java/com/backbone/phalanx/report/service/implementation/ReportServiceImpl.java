package com.backbone.phalanx.report.service.implementation;

import com.backbone.phalanx.report.model.ReportType;
import com.backbone.phalanx.report.service.ReportGenerator;
import com.backbone.phalanx.report.service.ReportService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Log4j2
public class ReportServiceImpl implements ReportService {

    private final Map<ReportType, ReportGenerator> reportGenerators = new HashMap<>();

    public ReportServiceImpl(List<ReportGenerator> reportGenerators) {
        reportGenerators.forEach((generator) -> this.reportGenerators.put(generator.getReportType(), generator));
    }

    public byte[] generateReport(ReportType reportType) {
        try {
            return reportGenerators.get(reportType).generateReport();
        } catch (Exception exception) {
            log.error("Error generating report: {}", exception.getMessage());
            throw new RuntimeException(exception);
        }
    }
}