package com.backbone.phalanx.inventarization.converter;

import com.backbone.phalanx.inventarization.model.InventarizationStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class InventarizationStatusConverter implements AttributeConverter<InventarizationStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(InventarizationStatus status) {
        return status != null ? status.getValue() : null;
    }

    @Override
    public InventarizationStatus convertToEntityAttribute(Integer statusCode) {
        return statusCode != null ? InventarizationStatus.fromCode(statusCode) : null;
    }
}
