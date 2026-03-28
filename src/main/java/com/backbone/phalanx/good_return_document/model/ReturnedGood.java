package com.backbone.phalanx.good_return_document.model;

import com.backbone.phalanx.outbound_document.model.OutboundGood;
import com.backbone.phalanx.product.converter.UnitConverter;
import com.backbone.phalanx.product.model.Category;
import com.backbone.phalanx.product.model.Unit;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Table(name = "returned_good")
public class ReturnedGood {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "external_id", nullable = false, unique = true)
    private String externalId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "barcode")
    private String barcode;

    @Column(name = "unit", nullable = false)
    @Convert(converter = UnitConverter.class)
    private Unit unit;

    @OneToOne
    @NotNull
    private Category category;

    @Column(name = "purchased_price", nullable = false)
    private BigDecimal purchasedPrice;

    @Column(name = "selling_price", nullable = false)
    private BigDecimal sellingPrice;

    @Column(name = "quantity", nullable = false)
    private BigDecimal quantity;

    @ManyToOne
    @JoinColumn(name = "good_return_document_id", nullable = false)
    private GoodReturnDocument goodReturnDocument;

    @ManyToOne
    @JoinColumn(name = "outbound_good_id")
    private OutboundGood outboundGood;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
