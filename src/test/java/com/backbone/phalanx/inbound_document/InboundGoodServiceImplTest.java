package com.backbone.phalanx.inbound_document;

import com.backbone.phalanx.inbound_document.model.InboundGood;
import com.backbone.phalanx.inbound_document.repository.InboundGoodRepository;
import com.backbone.phalanx.inbound_document.service.implementation.InboundGoodServiceImpl;
import com.backbone.phalanx.product.model.Product;
import com.backbone.phalanx.product.repository.ProductRepository;
import com.backbone.phalanx.product.service.CategoryService;
import com.backbone.phalanx.inbound_document.mapper.InboundDocumentMapper;
import com.backbone.phalanx.exception.ProductStockBalanceNotSufficientException;
import com.backbone.phalanx.inbound_document.dto.InboundGoodUpdateRequestDto;
import com.backbone.phalanx.inbound_document.dto.InboundGoodResponseDto;
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

    @Mock
    private CategoryService categoryService;

    @Mock
    private InboundDocumentMapper inboundDocumentMapper;

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

    @Test
    void testDeleteInboundGood_InsufficientStockThrowsException() {
        String externalId = "good-uuid";
        String barcode = "barcode-123";

        InboundGood good = InboundGood.builder()
                .externalId(externalId)
                .barcode(barcode)
                .quantity(BigDecimal.valueOf(10))
                .build();

        Product product = Product.builder()
                .name("Test Product")
                .barcode(barcode)
                .stockBalance(BigDecimal.valueOf(5)) // Less than good quantity (10)
                .build();

        when(inboundGoodRepository.findByExternalId(externalId)).thenReturn(Optional.of(good));
        when(productRepository.findByBarcode(barcode)).thenReturn(Optional.of(product));

        assertThrows(ProductStockBalanceNotSufficientException.class,
                () -> inboundGoodService.deleteInboundGood(externalId));

        verify(productRepository, never()).save(any());
        verify(inboundGoodRepository, never()).delete(any());
    }

    @Test
    void testUpdateInboundGood_Success() {
        String externalId = "good-uuid";
        String barcode = "barcode-123";

        InboundGood good = InboundGood.builder()
                .externalId(externalId)
                .barcode(barcode)
                .purchasedPrice(BigDecimal.valueOf(100.00))
                .sellingPrice(BigDecimal.valueOf(120.00))
                .quantity(BigDecimal.valueOf(5))
                .build();

        Product product = Product.builder()
                .barcode(barcode)
                .purchasedPrice(BigDecimal.valueOf(150.00))
                .sellingPrice(BigDecimal.valueOf(180.00))
                .stockBalance(BigDecimal.valueOf(10))
                .build();

        InboundGoodUpdateRequestDto request =
                new InboundGoodUpdateRequestDto(
                        externalId,
                        BigDecimal.valueOf(110.00), // new purchased price
                        BigDecimal.valueOf(130.00), // new selling price
                        BigDecimal.valueOf(7),      // new quantity (stock diff = +2)
                        null, null, null
                );

        when(inboundGoodRepository.findByExternalId(externalId)).thenReturn(Optional.of(good));
        when(productRepository.findByBarcode(barcode)).thenReturn(Optional.of(product));
        when(inboundGoodRepository.save(any(InboundGood.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(inboundDocumentMapper.toGoodDto(any(InboundGood.class))).thenAnswer(invocation -> {
            InboundGood saved = invocation.getArgument(0);
            return InboundGoodResponseDto.builder()
                    .externalId(saved.getExternalId())
                    .name(saved.getName())
                    .sku(saved.getSku())
                    .barcode(saved.getBarcode())
                    .unit(saved.getUnit() != null ? saved.getUnit().toString() : null)
                    .purchasedPrice(saved.getPurchasedPrice())
                    .sellingPrice(saved.getSellingPrice())
                    .quantity(saved.getQuantity())
                    .photoUrl(saved.getPhotoUrl())
                    .createdAt(saved.getCreatedAt())
                    .updatedAt(saved.getUpdatedAt())
                    .build();
        });

        InboundGoodResponseDto response =
                inboundGoodService.updateInboundGood(request);

        assertNotNull(response);
        // Stock balance: 10 (old) - 5 (old good) + 7 (new good) = 12
        // Purchased price recalculation:
        // old total without old good = (150 * 10) - (100 * 5) = 1500 - 500 = 1000
        // new total with new good = 1000 + (110 * 7) = 1000 + 770 = 1770
        // expected purchased price = 1770 / 12 = 147.50
        assertEquals(BigDecimal.valueOf(12), product.getStockBalance());
        assertEquals(BigDecimal.valueOf(147.50).setScale(2), product.getPurchasedPrice());
        assertEquals(BigDecimal.valueOf(130.00), product.getSellingPrice());

        verify(productRepository).save(product);
        verify(inboundGoodRepository).save(good);
    }

    @Test
    void testUpdateInboundGood_InsufficientStockThrowsException() {
        String externalId = "good-uuid";
        String barcode = "barcode-123";

        InboundGood good = InboundGood.builder()
                .externalId(externalId)
                .barcode(barcode)
                .purchasedPrice(BigDecimal.valueOf(100.00))
                .quantity(BigDecimal.valueOf(10))
                .build();

        Product product = Product.builder()
                .name("Test Product")
                .barcode(barcode)
                .purchasedPrice(BigDecimal.valueOf(150.00))
                .stockBalance(BigDecimal.valueOf(5)) // Only 5 in stock
                .build();

        // Update request reduces quantity from 10 to 3 (diff = -7, resulting stock = 5 - 10 + 3 = -2)
        InboundGoodUpdateRequestDto request =
                new InboundGoodUpdateRequestDto(
                        externalId,
                        BigDecimal.valueOf(100.00),
                        BigDecimal.valueOf(120.00),
                        BigDecimal.valueOf(3),
                        null, null, null
                );

        when(inboundGoodRepository.findByExternalId(externalId)).thenReturn(Optional.of(good));
        when(productRepository.findByBarcode(barcode)).thenReturn(Optional.of(product));

        assertThrows(ProductStockBalanceNotSufficientException.class,
                () -> inboundGoodService.updateInboundGood(request));

        verify(productRepository, never()).save(any());
        verify(inboundGoodRepository, never()).save(any());
    }
}
