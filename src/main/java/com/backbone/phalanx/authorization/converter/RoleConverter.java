package com.backbone.phalanx.authorization.converter;

import com.backbone.phalanx.authorization.model.Role;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class RoleConverter implements AttributeConverter<Role, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Role role) {
        return role.getValue();
    }

    @Override
    public Role convertToEntityAttribute(Integer roleStatusCode) {
        return Role.fromCode(roleStatusCode);
    }
}
