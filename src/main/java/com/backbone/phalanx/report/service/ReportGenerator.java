package com.backbone.phalanx.report.service;

import com.backbone.phalanx.report.model.ReportType;

import java.io.IOException;

public interface ReportGenerator {

    /**
     * Retrieves the type of report associated with the implementation of the service.
     *
     * @return the type of report as a {@code ReportType} object
     */
    ReportType getReportType();

    /**
     * Generates a report based on the specific implementation of the service.
     *
     * @return a byte array representing the generated report
     * @throws IOException if an input or output exception occurs during report generation
     */
    byte[] generateReport() throws IOException;
}