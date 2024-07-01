package com.rentals.apartment.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RoleUnitTest {

    @Test
    void shouldReturnTheRightRole_fromIdTest() {
        Role user = Role.fromId(1L);
        Role admin = Role.fromId(2L);

        Assertions.assertEquals(Role.USER, user);
        Assertions.assertEquals(Role.ADMIN, admin);
    }

    @Test
    void shouldReturnTheRightRole_ValueOfTest() {
        Role user = Role.valueOf("USER");
        Role admin = Role.valueOf("ADMIN");

        Assertions.assertEquals(Role.USER, user);
        Assertions.assertEquals(Role.ADMIN, admin);
    }

    @Test
    void shouldThrowAnError_fromIdTest() {
        Assertions.assertThrows(UnsupportedOperationException.class,
                () -> Role.fromId(3L));
        Assertions.assertThrows(UnsupportedOperationException.class,
                () -> Role.fromId(null));
        Assertions.assertThrows(UnsupportedOperationException.class,
                () -> Role.fromId(0L));
        Assertions.assertThrows(UnsupportedOperationException.class,
                () -> Role.fromId(12345L));
    }

    @Test
    void shouldThrowAnError_valueOf() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> Role.valueOf("1"));
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> Role.valueOf("Admin"));
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> Role.valueOf("admin"));
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> Role.valueOf("User"));
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> Role.valueOf("user"));
    }
}
