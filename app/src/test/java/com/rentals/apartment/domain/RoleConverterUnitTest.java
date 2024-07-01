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
        String user = roleConverter.convertToDatabaseColumn(Role.USER);
        Assertions.assertEquals("1", user);
    }

    @Test
    void shouldConvertToDatabaseColumnForAdmin() {
        String admin = roleConverter.convertToDatabaseColumn(Role.ADMIN);
        Assertions.assertEquals("2", admin);
    }

    @Test
    void shouldSetRoleAsDefaultUser_ConvertToDatabaseColumn() {
        String nullRole = roleConverter.convertToDatabaseColumn(null);
        Assertions.assertEquals("1", nullRole);
    }

    @Test
    void shouldConvertToEntityAttributeForUser() {
        Role user = roleConverter.convertToEntityAttribute("1");

        Assertions.assertEquals(Role.USER, user);
    }

    @Test
    void shouldConvertToEntityAttributeForAdmin() {
        Role admin = roleConverter.convertToEntityAttribute("2");

        Assertions.assertEquals(Role.ADMIN, admin);
    }

    @Test
    void shouldThrowAnError_WhenTryToConvertToEntityAttributeForNullId() {
        Assertions.assertThrows(UnsupportedMappingException.class,
                () -> roleConverter.convertToEntityAttribute(null));
    }
}
