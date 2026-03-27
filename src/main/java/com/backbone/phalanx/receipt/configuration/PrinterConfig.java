package com.backbone.phalanx.receipt.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "phalanx.printer")
@Data
public class PrinterConfig {

    /**
     * Name of the printer to use. If null or empty, the first available receipt
     * printer will be used.
     */
    private String name;

    /**
     * Character encoding for the printer. Default is "CP866" (common for Russian
     * Cyrillic ESC/POS).
     */
    private String encoding = "CP866";
}