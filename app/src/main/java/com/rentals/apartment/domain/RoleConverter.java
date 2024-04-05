package com.rentals.apartment.domain;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Objects;

@Converter
public class RoleConverter implements AttributeConverter<Role, Long> {
    @Override
    public Long convertToDatabaseColumn(Role role) {
        if (Objects.isNull(role)) {
            return null;
        }
        return role.getId();
    }

    @Override
    public Role convertToEntityAttribute(Long id) {
        if (Objects.isNull(id)) {
            return null;
        }
        return Role.fromId(id);
    }
}
