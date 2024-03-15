package com.rentals.apartment.domain;

import java.util.Objects;

public class ApartmentFilter {

    private final String bedrooms;
    private final String bathrooms;
    private final Float minArea;
    private final Float maxArea;
    private final Float minPrice;
    private final Float maxPrice;
    private final Boolean hasParking;
    private final String description;

    public ApartmentFilter(String bedrooms, String bathrooms, Float minArea, Float maxArea, Float minPrice, Float maxPrice, Boolean hasParking, String description) {
        this.bedrooms = bedrooms;
        this.bathrooms = bathrooms;
        this.minArea = minArea;
        this.maxArea = maxArea;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.hasParking = hasParking;
        this.description = description;
    }

    public String getBedrooms() {
        return bedrooms;
    }

    public String getBathrooms() {
        return bathrooms;
    }

    public Float getMinArea() {
        return minArea;
    }

    public Float getMaxArea() {
        return maxArea;
    }

    public Float getMinPrice() {
        return minPrice;
    }

    public Float getMaxPrice() {
        return maxPrice;
    }

    public Boolean getHasParking() {
        return hasParking;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApartmentFilter that = (ApartmentFilter) o;
        return Objects.equals(bedrooms, that.bedrooms) &&
                Objects.equals(bathrooms, that.bathrooms) &&
                Objects.equals(minArea, that.minArea) &&
                Objects.equals(maxArea, that.maxArea) &&
                Objects.equals(minPrice, that.minPrice) &&
                Objects.equals(maxPrice, that.maxPrice) &&
                Objects.equals(hasParking, that.hasParking) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bedrooms, bathrooms, minArea, maxArea, minPrice, maxPrice, hasParking, description);
    }

    @Override
    public String toString() {
        return String.format("Bedrooms: %s\nBathrooms: %s\nArea: %.2f to %.2f\nPrice: %.2f to %.2f\nHas Parking: %b\nDescription: %s",
                bedrooms, bathrooms, minArea, maxArea, minPrice, maxPrice, hasParking, description);
    }
}
