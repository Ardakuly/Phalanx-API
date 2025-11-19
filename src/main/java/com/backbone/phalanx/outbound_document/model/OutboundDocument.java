package com.backbone.phalanx.outbound_document.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Table(name = "outbound_document")
public class OutboundDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "external_id", nullable = false, unique = true)
    private String externalId;

    @Column(name = "document_number", nullable = false, unique = true)
    private String documentNumber;

    @OneToMany(mappedBy = "outboundDocument", cascade = CascadeType.REMOVE)
    private List<OutboundGood> outboundGoods;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
