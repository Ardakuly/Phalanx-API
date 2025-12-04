package com.backbone.phalanx.outbound_document.converter;

import com.backbone.phalanx.outbound_document.model.PaymentType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;


@Converter(autoApply = true)
public class PaymentTypeConverter implements AttributeConverter<PaymentType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(PaymentType paymentType) {
        return paymentType.getValue();
    }

    @Override
    public PaymentType convertToEntityAttribute(Integer paymentTypeCode) {
        return PaymentType.fromCode(paymentTypeCode);
    }
}