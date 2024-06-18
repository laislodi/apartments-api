package com.rentals.apartment.domain;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Objects;

@Converter
public class RoleConverter implements AttributeConverter<Role, String> {
    @Override
    public String convertToDatabaseColumn(Role role) {
        if (Objects.isNull(role)) {
            return null;
        }
        return role.getId().toString();
    }

    @Override
    public Role convertToEntityAttribute(String id) {
        if (Objects.isNull(id)) {
            return null;
        }
        return Role.fromId(id);
    }
}
