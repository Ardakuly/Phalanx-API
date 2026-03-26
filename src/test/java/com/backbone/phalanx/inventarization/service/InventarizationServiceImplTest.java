package com.backbone.phalanx.inventarization.service;

import com.backbone.phalanx.inventarization.dto.CountRequestDto;
import com.backbone.phalanx.inventarization.dto.InventarizationItemResponseDto;
import com.backbone.phalanx.inventarization.dto.InventarizationResponseDto;
import com.backbone.phalanx.inventarization.mapper.InventarizationMapper;
import com.backbone.phalanx.inventarization.model.Inventarization;
import com.backbone.phalanx.inventarization.model.InventarizationItem;
import com.backbone.phalanx.inventarization.model.InventarizationStatus;
import com.backbone.phalanx.inventarization.repository.InventarizationRepository;
import com.backbone.phalanx.product.model.Product;
import com.backbone.phalanx.product.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class InventarizationServiceImplTest {

    @Mock
    private InventarizationRepository inventarizationRepository;

    @Mock
    private InventarizationItemService inventarizationItemService;

    @Mock
    private ProductService productService;

    @Mock
    private InventarizationMapper inventarizationMapper;

    @InjectMocks
    private InventarizationServiceImpl inventarizationService;

    @BeforeEach
    void setUp() {
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        lenient().when(authentication.isAuthenticated()).thenReturn(true);
        lenient().when(authentication.getPrincipal()).thenReturn("testUser");
        lenient().when(authentication.getName()).thenReturn("testUser");
        SecurityContextHolder.setContext(securityContext);
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    // ================== START METHOD TESTS ==================

    @Test
    void testStart_Success() {
        when(inventarizationRepository.existsByStatus(InventarizationStatus.IN_PROGRESS)).thenReturn(false);

        Inventarization savedInventarization = new Inventarization();
        savedInventarization.setId(1L);
        savedInventarization.setStatus(InventarizationStatus.IN_PROGRESS);

        when(inventarizationRepository.save(any(Inventarization.class))).thenReturn(savedInventarization);

        Product product = new Product();
        product.setId(10L);
        product.setStockBalance(BigDecimal.valueOf(50));
        when(productService.getAllProductEntities()).thenReturn(List.of(product));

        InventarizationResponseDto responseDto = mock(InventarizationResponseDto.class);
        
        when(inventarizationRepository.findById(1L)).thenReturn(Optional.of(savedInventarization));
        when(inventarizationItemService.findByInventarizationId(1L)).thenReturn(Collections.emptyList());
        when(inventarizationMapper.toDto(any(), any())).thenReturn(responseDto);

        InventarizationResponseDto result = inventarizationService.start();

        assertNotNull(result);
        verify(inventarizationRepository).existsByStatus(InventarizationStatus.IN_PROGRESS);
        verify(inventarizationRepository, times(1)).save(any(Inventarization.class));
        verify(inventarizationItemService, times(1)).saveAll(any());
    }

    @Test
    void testStart_AlreadyInProgress() {
        when(inventarizationRepository.existsByStatus(InventarizationStatus.IN_PROGRESS)).thenReturn(true);

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> inventarizationService.start());
        assertEquals("An inventarization session is already in progress.", exception.getMessage());
        
        verify(inventarizationRepository, never()).save(any());
    }

    // ================== COUNT METHOD TESTS ==================

    @Test
    void testCount_Success() {
        Long inventarizationId = 1L;
        Long productId = 10L;
        CountRequestDto req = new CountRequestDto(productId, BigDecimal.valueOf(100));

        Inventarization inventarization = new Inventarization();
        inventarization.setId(inventarizationId);
        inventarization.setStatus(InventarizationStatus.IN_PROGRESS);

        InventarizationItem item = new InventarizationItem();
        item.setId(100L);

        when(inventarizationRepository.findById(inventarizationId)).thenReturn(Optional.of(inventarization));
        when(inventarizationItemService.findByInventarizationIdAndProductId(inventarizationId, productId)).thenReturn(Optional.of(item));

        InventarizationResponseDto responseDto = mock(InventarizationResponseDto.class);
        when(inventarizationItemService.findByInventarizationId(1L)).thenReturn(List.of(item));
        when(inventarizationMapper.toDto(any(), any())).thenReturn(responseDto);

        InventarizationResponseDto result = inventarizationService.count(inventarizationId, req);

        assertNotNull(result);
        assertEquals(BigDecimal.valueOf(100), item.getCountedQuantity());
        verify(inventarizationItemService).save(item);
    }

    @Test
    void testCount_SessionNotFound() {
        Long id = 1L;
        when(inventarizationRepository.findById(id)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, 
            () -> inventarizationService.count(id, new CountRequestDto(10L, BigDecimal.ONE)));

        assertEquals("Inventarization session not found.", exception.getMessage());
    }

    @Test
    void testCount_NotInProgress() {
        Long id = 1L;
        Inventarization inventarization = new Inventarization();
        inventarization.setId(id);
        inventarization.setStatus(InventarizationStatus.COMPLETED); // Not IN_PROGRESS

        when(inventarizationRepository.findById(id)).thenReturn(Optional.of(inventarization));

        IllegalStateException exception = assertThrows(IllegalStateException.class, 
            () -> inventarizationService.count(id, new CountRequestDto(10L, BigDecimal.ONE)));

        assertEquals("Cannot count items for a session that is not in progress.", exception.getMessage());
    }
    
    @Test
    void testCount_ItemNotFound() {
        Long inventarizationId = 1L;
        Long productId = 10L;

        Inventarization inventarization = new Inventarization();
        inventarization.setId(inventarizationId);
        inventarization.setStatus(InventarizationStatus.IN_PROGRESS);

        when(inventarizationRepository.findById(inventarizationId)).thenReturn(Optional.of(inventarization));
        when(inventarizationItemService.findByInventarizationIdAndProductId(inventarizationId, productId)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, 
            () -> inventarizationService.count(inventarizationId, new CountRequestDto(productId, BigDecimal.ONE)));

        assertEquals("Item not found in this inventarization session.", exception.getMessage());
    }

    // ================== COMPLETE METHOD TESTS ==================

    @Test
    void testComplete_Success() {
        Long id = 1L;
        Inventarization inventarization = new Inventarization();
        inventarization.setId(id);
        inventarization.setStatus(InventarizationStatus.IN_PROGRESS);

        Product p1 = new Product(); p1.setId(10L); 
        
        InventarizationItem item = new InventarizationItem();
        item.setId(100L);
        item.setProduct(p1);
        item.setExpectedQuantity(BigDecimal.valueOf(10));
        item.setCountedQuantity(BigDecimal.valueOf(12)); 

        when(inventarizationRepository.findById(id)).thenReturn(Optional.of(inventarization));
        when(inventarizationItemService.findByInventarizationId(id)).thenReturn(List.of(item));

        InventarizationResponseDto responseDto = mock(InventarizationResponseDto.class);
        when(inventarizationMapper.toDto(any(), any())).thenReturn(responseDto);

        InventarizationResponseDto result = inventarizationService.complete(id);

        assertNotNull(result);
        assertEquals(InventarizationStatus.COMPLETED, inventarization.getStatus());
        assertEquals("testUser", inventarization.getClosedBy());
        
        verify(productService).updateProductStock(10L, BigDecimal.valueOf(12));
        verify(inventarizationItemService).saveAll(any());
        verify(inventarizationRepository).save(inventarization);
        
        assertEquals(BigDecimal.valueOf(2), item.getDifference());
        assertEquals(BigDecimal.valueOf(12), item.getFinalQuantity());
    }

    @Test
    void testComplete_SessionNotFound() {
        when(inventarizationRepository.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, 
            () -> inventarizationService.complete(1L));

        assertEquals("Inventarization session not found.", exception.getMessage());
    }

    @Test
    void testComplete_NotInProgress() {
        Inventarization inventarization = new Inventarization();
        inventarization.setId(1L);
        inventarization.setStatus(InventarizationStatus.COMPLETED);

        when(inventarizationRepository.findById(1L)).thenReturn(Optional.of(inventarization));

        IllegalStateException exception = assertThrows(IllegalStateException.class, 
            () -> inventarizationService.complete(1L));

        assertEquals("Cannot complete a session that is not in progress.", exception.getMessage());
    }
}
