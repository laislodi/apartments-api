package com.rentals.apartment.domain;

public record ApartmentDTO(
        String id,
        Integer numberOfBedrooms,
        Integer numberOfBathrooms,
        Float area,
        Boolean hasParking,
        Float price,
        String description
) {

    public String toJson() {
        return String.format("""
                {"id": "%s", "numberOfBedrooms": %d, "numberOfBathrooms": %d, "area": %.2f, "hasParking": %b, "price": %.2f, "description": "%s" }
                """, id, numberOfBedrooms, numberOfBathrooms, area, hasParking, price, description);
    }

    public ApartmentEntity toEntity() {
        ApartmentEntity bean = new ApartmentEntity();
        bean.setId(id);
        bean.setNumberOfBedrooms(numberOfBedrooms);
        bean.setNumberOfBathrooms(numberOfBathrooms);
        bean.setArea(area);
        bean.setHasParking(hasParking);
        bean.setPrice(price);
        bean.setDescription(description);
        return bean;
    }
}
