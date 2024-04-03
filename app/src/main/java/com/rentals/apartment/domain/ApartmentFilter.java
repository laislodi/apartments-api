package com.rentals.apartment.domain;

public record ApartmentFilter(String bedrooms, String bathrooms, Float minArea, Float maxArea, Float minPrice,
                              Float maxPrice, Boolean hasParking, String description) {

    @Override
    public String toString() {
        return String.format("Bedrooms: %s, Bathrooms: %s, Area: %.2f to %.2f, Price: %.2f to %.2f, Has Parking: %b, Description: %s",
                bedrooms, bathrooms, minArea, maxArea, minPrice, maxPrice, hasParking, description);
    }
}
