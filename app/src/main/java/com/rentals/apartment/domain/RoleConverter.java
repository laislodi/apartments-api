package com.rentals.apartment.domain;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.hibernate.metamodel.UnsupportedMappingException;

import java.util.Objects;

@Converter
public class RoleConverter implements AttributeConverter<Role, Long> {
    @Override
    public Long convertToDatabaseColumn(Role role) {
        if (Objects.isNull(role)) {
            return Role.USER.getId();
        }
        return role.getId();
    }

    @Override
    public Role convertToEntityAttribute(Long id) {
        if (Objects.isNull(id)) {
            throw new UnsupportedMappingException("Unsupported nulled Id for Role!");
        }
        return Role.fromId(id);
    }
}
