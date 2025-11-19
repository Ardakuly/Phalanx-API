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

    private String externalId;

    private String documentNumber;

    @OneToMany(mappedBy = "outboundDocument", cascade = CascadeType.REMOVE)
    private List<OutboundGood> outboundGoods;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
