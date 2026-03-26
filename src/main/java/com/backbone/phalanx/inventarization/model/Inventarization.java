package com.backbone.phalanx.inventarization.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import com.backbone.phalanx.inventarization.converter.InventarizationStatusConverter;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "inventarization")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Inventarization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Convert(converter = InventarizationStatusConverter.class)
    @Column(nullable = false)
    private InventarizationStatus status;

    @Column(name = "started_at", nullable = false)
    private LocalDateTime startedAt;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    @Column(name = "conducted_by", nullable = false)
    private String conductedBy;

    @Column(name = "created_by", nullable = false)
    private String createdBy;

    @Column(name = "closed_by")
    private String closedBy;
}