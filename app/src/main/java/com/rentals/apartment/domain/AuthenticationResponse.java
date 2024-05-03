package com.rentals.apartment.domain;

public record AuthenticationResponse(String token, Role role) {
}
