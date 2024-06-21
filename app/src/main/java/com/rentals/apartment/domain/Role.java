package com.rentals.apartment.domain;

import jakarta.persistence.Convert;

public enum Role {
    USER(1L), ADMIN(2L);

    @Convert(converter = Long.class)
    private final Long id;

    Role(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public static Role fromId(String id) {
        for (Role role: Role.values()){
            if (role.getId().toString().equals(id)){
                return role;
            }
        }
        throw new UnsupportedOperationException(
                "The role id " + id + " is not supported!");
    }
}
