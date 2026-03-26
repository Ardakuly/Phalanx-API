package com.backbone.phalanx.inventarization.model;

import com.backbone.phalanx.product.model.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "inventarization_item")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventarizationItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inventarization_id", nullable = false)
    private Inventarization inventarization;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "expected_quantity", nullable = false)
    private BigDecimal expectedQuantity;

    @Column(name = "counted_quantity")
    private BigDecimal countedQuantity;

    @Column(name = "final_quantity")
    private BigDecimal finalQuantity;

    @Column(name = "difference")
    private BigDecimal difference;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}