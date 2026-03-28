package com.backbone.phalanx.good_return_document.model;

import com.backbone.phalanx.outbound_document.model.OutboundDocument;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Table(name = "good_return_document")
public class GoodReturnDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "external_id", nullable = false, unique = true)
    private String externalId;

    @Column(name = "document_number", nullable = false, unique = true)
    private String documentNumber;

    @ManyToOne
    @JoinColumn(name = "outbound_document_id", nullable = false)
    private OutboundDocument outboundDocument;

    @Column(name = "refund_amount", nullable = false)
    private BigDecimal refundAmount;

    @Column(name = "comment")
    private String comment;

    @OneToMany(mappedBy = "goodReturnDocument", cascade = CascadeType.ALL)
    private List<ReturnedGood> returnedGoods;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
