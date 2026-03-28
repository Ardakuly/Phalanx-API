package com.backbone.phalanx.receipt;

import com.backbone.phalanx.outbound_document.model.OutboundDocument;
import com.backbone.phalanx.outbound_document.model.PaymentType;
import com.backbone.phalanx.outbound_document.model.OutboundGood;
import com.backbone.phalanx.receipt.configuration.PrinterConfig;
import com.backbone.phalanx.receipt.service.implementation.ReceiptServiceImpl;
import com.backbone.phalanx.user.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReceiptServiceTest {

    @Mock
    private PrinterConfig printerConfig;

    @InjectMocks
    private ReceiptServiceImpl receiptService;

    private OutboundDocument document;

    @BeforeEach
    void setUp() {
        User seller = new User();
        seller.setEmail("seller@test.com");

        OutboundGood good = OutboundGood.builder()
                .name("Test Product")
                .quantity(new BigDecimal("2"))
                .sellingPrice(new BigDecimal("100"))
                .build();

        document = OutboundDocument.builder()
                .documentNumber("REC-123")
                .createdAt(LocalDateTime.now())
                .seller(seller)
                .paymentType(PaymentType.CASH)
                .outboundGoods(List.of(good))
                .build();
    }

    @Test
    void testPrintReceiptNoPrinterFoundSilentlyReturns() {
        when(printerConfig.getName()).thenReturn("NonExistentPrinter");
        
        // This should not throw exception even if printer not found
        receiptService.printReceipt(document);
    }
}