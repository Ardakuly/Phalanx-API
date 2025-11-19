package com.backbone.phalanx.inbound_document.model;

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
@Table(name = "inbound_document")
public class InboundDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String externalId;

    private String documentNumber;

    @OneToMany(mappedBy = "inboundDocument", cascade = CascadeType.ALL)
    private List<InboundGood> inboundGoods;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
