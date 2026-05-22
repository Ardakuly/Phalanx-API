package com.backbone.phalanx.inbound_document;

import com.backbone.phalanx.inbound_document.model.InboundGood;
import com.backbone.phalanx.inbound_document.repository.InboundGoodRepository;
import com.backbone.phalanx.inbound_document.service.implementation.InboundGoodServiceImpl;
import com.backbone.phalanx.product.model.Product;
import com.backbone.phalanx.product.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InboundGoodServiceImplTest {

    @Mock
    private InboundGoodRepository inboundGoodRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private InboundGoodServiceImpl inboundGoodService;

    @Test
    void testDeleteInboundGood_Success() {
        String externalId = "good-uuid";
        String barcode = "barcode-123";

        InboundGood good = InboundGood.builder()
                .externalId(externalId)
                .barcode(barcode)
                .purchasedPrice(BigDecimal.valueOf(100.00))
                .quantity(BigDecimal.valueOf(5))
                .build();

        Product product = Product.builder()
                .barcode(barcode)
                .purchasedPrice(BigDecimal.valueOf(150.00))
                .stockBalance(BigDecimal.valueOf(10))
                .build();

        when(inboundGoodRepository.findByExternalId(externalId)).thenReturn(Optional.of(good));
        when(productRepository.findByBarcode(barcode)).thenReturn(Optional.of(product));

        inboundGoodService.deleteInboundGood(externalId);

        // Verification of calculations:
        // currentTotal = 150 * 10 = 1500
        // oldGoodEffect = 100 * 5 = 500
        // totalWithoutOld = 1500 - 500 = 1000
        // stockWithoutOld = 10 - 5 = 5
        // expectedPurchasedPrice = 1000 / 5 = 200.00
        assertEquals(BigDecimal.valueOf(5), product.getStockBalance());
        assertEquals(BigDecimal.valueOf(200.00).setScale(2), product.getPurchasedPrice());

        verify(productRepository).save(product);
        verify(inboundGoodRepository).delete(good);
    }

    @Test
    void testDeleteInboundGood_NegativeOrZeroStock() {
        String externalId = "good-uuid";
        String barcode = "barcode-123";

        InboundGood good = InboundGood.builder()
                .externalId(externalId)
                .barcode(barcode)
                .purchasedPrice(BigDecimal.valueOf(100.00))
                .quantity(BigDecimal.valueOf(10))
                .build();

        // Product stock balance is same or less than good quantity
        Product product = Product.builder()
                .barcode(barcode)
                .purchasedPrice(BigDecimal.valueOf(150.00))
                .stockBalance(BigDecimal.valueOf(10))
                .build();

        when(inboundGoodRepository.findByExternalId(externalId)).thenReturn(Optional.of(good));
        when(productRepository.findByBarcode(barcode)).thenReturn(Optional.of(product));

        inboundGoodService.deleteInboundGood(externalId);

        assertEquals(BigDecimal.ZERO, product.getStockBalance());
        assertEquals(BigDecimal.ZERO, product.getPurchasedPrice());

        verify(productRepository).save(product);
        verify(inboundGoodRepository).delete(good);
    }

    @Test
    void testDeleteInboundGood_NotFound() {
        String externalId = "non-existent";
        when(inboundGoodRepository.findByExternalId(externalId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> inboundGoodService.deleteInboundGood(externalId));

        verify(productRepository, never()).save(any());
        verify(inboundGoodRepository, never()).delete(any());
    }
}
