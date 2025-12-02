package com.backbone.phalanx.report.service;

import java.io.IOException;

public interface ReportService {

    /**
     * Generates and returns the leftover report in binary format.
     *
     * @return a byte array representing the leftover report data
     */
    byte[] getLeftoverReport() throws IOException;

    /**
     * Generates a PDF document containing transaction details.
     *
     * @return a byte array representing the generated PDF document of transactions
     */
    byte[] getTransactionsPdf();
}
