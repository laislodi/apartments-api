package com.rentals.apartment.domain;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.hibernate.metamodel.UnsupportedMappingException;

import java.util.Objects;

@Converter
public class RoleConverter implements AttributeConverter<Role, String> {
    @Override
    public String convertToDatabaseColumn(Role role) {
        if (Objects.isNull(role)) {
            return Role.USER.getId().toString();
        }
        return role.getId().toString();
    }

    @Override
    public Role convertToEntityAttribute(String id) {
        if (Objects.isNull(id)) {
            throw new UnsupportedMappingException("Unsupported nulled Id for Role!");
        }
        return Role.fromId(id);
    }
}
