package com.rentals.apartment;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("properties")
public record ApartmentsConfigProperties(String apiUrl, String apiVersion) {
}
