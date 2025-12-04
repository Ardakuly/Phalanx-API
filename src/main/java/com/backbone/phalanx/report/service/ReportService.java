package com.backbone.phalanx.report.service;

import com.backbone.phalanx.report.model.ReportType;

public interface ReportService {

    /**
     * Generates a report based on the specified report type.
     *
     * @param reportType the type of report to be generated, represented by the {@code ReportType} enum
     * @return a byte array containing the generated report
     */
    byte[] generateReport(ReportType reportType);
}