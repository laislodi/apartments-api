package com.rentals.apartment.domain;

import com.rentals.apartment.domain.Role;
import com.rentals.apartment.domain.RoleConverter;
import org.hibernate.metamodel.UnsupportedMappingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RoleConverterUnitTest {

    RoleConverter roleConverter = new RoleConverter();

    @Test
    void shouldConvertToDatabaseColumnForUser() {
        Long user = roleConverter.convertToDatabaseColumn(Role.USER);
        Assertions.assertEquals(1L, user);
    }

    @Test
    void shouldConvertToDatabaseColumnForAdmin() {
        Long admin = roleConverter.convertToDatabaseColumn(Role.ADMIN);
        Assertions.assertEquals(2L, admin);
    }

    @Test
    void shouldSetRoleAsDefaultUser_ConvertToDatabaseColumn() {
        Long nullRole = roleConverter.convertToDatabaseColumn(null);
        Assertions.assertEquals(1L, nullRole);
    }

    @Test
    void shouldConvertToEntityAttributeForUser() {
        Role user = roleConverter.convertToEntityAttribute(1L);

        Assertions.assertEquals(Role.USER, user);
    }

    @Test
    void shouldConvertToEntityAttributeForAdmin() {
        Role admin = roleConverter.convertToEntityAttribute(2L);

        Assertions.assertEquals(Role.ADMIN, admin);
    }

    @Test
    void shouldThrowAnError_WhenTryToConvertToEntityAttributeForNullId() {
        Assertions.assertThrows(UnsupportedMappingException.class,
                () -> roleConverter.convertToEntityAttribute(null));
    }
}
