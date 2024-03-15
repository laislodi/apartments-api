package com.rentals.apartment.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "apartments")
public class ApartmentEntity {
    @Id
    private String id;
    private Integer numberOfBedrooms;
    private Integer numberOfBathrooms;
    private Float area;
    private Boolean hasParking;
    private Float price;
    private String description;


    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public Integer getNumberOfBedrooms() {
        return numberOfBedrooms;
    }

    public void setNumberOfBedrooms(Integer numberOfBedrooms) {
        this.numberOfBedrooms = numberOfBedrooms;
    }

    public Integer getNumberOfBathrooms() {
        return numberOfBathrooms;
    }

    public void setNumberOfBathrooms(Integer numberOfBathrooms) {
        this.numberOfBathrooms = numberOfBathrooms;
    }

    public Float getArea() {
        return area;
    }

    public void setArea(Float area) {
        this.area = area;
    }

    public Boolean getHasParking() {
        return hasParking;
    }

    public void setHasParking(Boolean hasParking) {
        this.hasParking = hasParking;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
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
        ApartmentEntity entity = (ApartmentEntity) o;
        return Objects.equals(id, entity.id) &&
                Objects.equals(numberOfBedrooms, entity.numberOfBedrooms) &&
                Objects.equals(numberOfBathrooms, entity.numberOfBathrooms) &&
                Objects.equals(area, entity.area) &&
                Objects.equals(hasParking, entity.hasParking) &&
                Objects.equals(price, entity.price) &&
                Objects.equals(description, entity.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, numberOfBedrooms, numberOfBathrooms, area, hasParking, price, description);
    }

    public ApartmentDTO toRecord() {
        return new ApartmentDTO(id, numberOfBedrooms, numberOfBathrooms, area, hasParking, price, description);
    }

    public static List<ApartmentDTO> toRecord(List<ApartmentEntity> entities) {
        List<ApartmentDTO> records = new ArrayList<>();
        entities.forEach(entity -> records.add(
                new ApartmentDTO(
                        entity.id, entity.numberOfBedrooms, entity.numberOfBathrooms, entity.area, entity.hasParking, entity.price, entity.description)
                )
        );
        return records;
    }
}
