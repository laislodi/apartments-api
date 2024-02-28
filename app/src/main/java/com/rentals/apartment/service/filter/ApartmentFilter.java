package com.rentals.apartment.service.filter;


import java.util.Objects;

public class ApartmentFilter {
    private MinimumFilter bedrooms;
    private MinimumFilter bathrooms;
    private RangeFilter area;
    private Boolean hasParking;
    private RangeFilter price;
    private String description;

    public ApartmentFilter(MinimumFilter bedrooms, MinimumFilter bathrooms, RangeFilter area, Boolean hasParking, RangeFilter price, String description) {
        this.bedrooms = bedrooms;
        this.bathrooms = bathrooms;
        this.area = area;
        this.hasParking = hasParking;
        this.price = price;
        this.description = description;
    }

    public MinimumFilter getBedrooms() {
        return bedrooms;
    }

    public void setBedrooms(MinimumFilter bedrooms) {
        this.bedrooms = bedrooms;
    }

    public MinimumFilter getBathrooms() {
        return bathrooms;
    }

    public void setBathrooms(MinimumFilter bathrooms) {
        this.bathrooms = bathrooms;
    }

    public RangeFilter getArea() {
        return area;
    }

    public void setArea(RangeFilter area) {
        this.area = area;
    }

    public Boolean getHasParking() {
        return hasParking;
    }

    public void setHasParking(Boolean hasParking) {
        this.hasParking = hasParking;
    }

    public RangeFilter getPrice() {
        return price;
    }

    public void setPrice(RangeFilter price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApartmentFilter that = (ApartmentFilter) o;
        return Objects.equals(bedrooms, that.bedrooms) &&
                Objects.equals(bathrooms, that.bathrooms) &&
                Objects.equals(area, that.area) &&
                Objects.equals(hasParking, that.hasParking) &&
                Objects.equals(price, that.price) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bedrooms, bathrooms, area, hasParking, price, description);
    }
}


