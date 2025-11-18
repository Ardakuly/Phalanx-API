package com.backbone.phalanx.product.converter;

import com.backbone.phalanx.product.model.Unit;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class UnitConverter implements AttributeConverter<Unit, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Unit suppliersEmployeeStatus) {
        return suppliersEmployeeStatus.getValue();
    }

    @Override
    public Unit convertToEntityAttribute(Integer suppliersEmployeeStatusCode) {
        return Unit.fromCode(suppliersEmployeeStatusCode);
    }
}