package com.rentals.apartment.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Objects;

@Entity
@Table(name = "apartments")
public class ApartmentBean {
    @Id
    private String id;
    private Integer numberOfBedrooms;
    private Integer numberOfBathrooms;
    private Double area;
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

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
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
        ApartmentBean bean = (ApartmentBean) o;
        return Objects.equals(id, bean.id) && Objects.equals(numberOfBedrooms, bean.numberOfBedrooms) && Objects.equals(numberOfBathrooms, bean.numberOfBathrooms) && Objects.equals(area, bean.area) && Objects.equals(hasParking, bean.hasParking) && Objects.equals(price, bean.price) && Objects.equals(description, bean.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, numberOfBedrooms, numberOfBathrooms, area, hasParking, price, description);
    }

    public ApartmentRecord toRecord() {
        return new ApartmentRecord(id, numberOfBedrooms, numberOfBathrooms, area, hasParking, price, description);
    }
}
