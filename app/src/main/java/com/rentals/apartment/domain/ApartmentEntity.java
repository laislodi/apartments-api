package com.rentals.apartment.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Objects;

@Entity
@Table(name = "apartments")
public class ApartmentEntity {
    @Id
    private String id;
    private Integer numberOfBedrooms;
    private Integer numberOfBathrooms;
    private Double area;
    private Boolean hasParking;
    private Float price;
    private String description;

    public ApartmentEntity() {
    }

    public ApartmentEntity setId(String id) {
        this.id = id;
        return this;
    }

    public String getId() {
        return id;
    }

    public Integer getNumberOfBedrooms() {
        return numberOfBedrooms;
    }

    public ApartmentEntity setNumberOfBedrooms(Integer numberOfBedrooms) {
        this.numberOfBedrooms = numberOfBedrooms;
        return this;
    }

    public Integer getNumberOfBathrooms() {
        return numberOfBathrooms;
    }

    public ApartmentEntity setNumberOfBathrooms(Integer numberOfBathrooms) {
        this.numberOfBathrooms = numberOfBathrooms;
        return this;
    }

    public Double getArea() {
        return area;
    }

    public ApartmentEntity setArea(Double area) {
        this.area = area;
        return this;
    }

    public Boolean getHasParking() {
        return hasParking;
    }

    public ApartmentEntity setHasParking(Boolean hasParking) {
        this.hasParking = hasParking;
        return this;
    }

    public Float getPrice() {
        return price;
    }

    public ApartmentEntity setPrice(Float price) {
        this.price = price;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ApartmentEntity setDescription(String description) {
        this.description = description;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApartmentEntity bean = (ApartmentEntity) o;
        return Objects.equals(id, bean.id) && Objects.equals(numberOfBedrooms, bean.numberOfBedrooms) && Objects.equals(numberOfBathrooms, bean.numberOfBathrooms) && Objects.equals(area, bean.area) && Objects.equals(hasParking, bean.hasParking) && Objects.equals(price, bean.price) && Objects.equals(description, bean.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, numberOfBedrooms, numberOfBathrooms, area, hasParking, price, description);
    }

    public ApartmentDTO toDTO() {
        return new ApartmentDTO(id, numberOfBedrooms, numberOfBathrooms, area, hasParking, price, description);
    }
}
