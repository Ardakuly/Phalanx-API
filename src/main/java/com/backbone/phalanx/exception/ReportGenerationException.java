package com.backbone.phalanx.exception;

import com.backbone.phalanx.report.model.ReportType;

public class ReportGenerationException extends RuntimeException {

    ReportGenerationException(ReportType reportType) {
        super("Error generating report of type: " + reportType + ".");
    }
}