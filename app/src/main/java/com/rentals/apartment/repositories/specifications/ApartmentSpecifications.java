package com.rentals.apartment.repositories.specifications;

import com.rentals.apartment.domain.ApartmentEntity;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ApartmentSpecifications {

    public Specification<ApartmentEntity> descriptionContains(String description) {
        String finalDescription = description.toLowerCase();
        return (apartment, cq, cb) -> cb.like(cb.lower(apartment.get("description")), "%" + finalDescription + "%");
    }

    private Specification<ApartmentEntity> greaterThanOrEqualTo(String value, String field) {
        return (apartment, cq, cb) -> {
            boolean greaterThan = value.contains("+");
            Integer val = Integer.parseInt(value.replace("+", ""));
            if (greaterThan) {
                return cb.greaterThanOrEqualTo(apartment.get(field), val);
            }
            return cb.equal(apartment.get(field), val);
        };
    }

    public Specification<ApartmentEntity> bedroomsEqualOrGreaterThan(String bedrooms) {
        return greaterThanOrEqualTo(bedrooms, "numberOfBedrooms");
    }

    public Specification<ApartmentEntity> bathroomsEqualOrGreaterThan(String bathrooms) {
        return greaterThanOrEqualTo(bathrooms, "numberOfBathrooms");
    }

    public Specification<ApartmentEntity> between(Float min, Float max, String field) {
        return (apartment, cq, cb) -> cb.and(
                cb.greaterThanOrEqualTo(apartment.get(field), min),
                cb.lessThanOrEqualTo(apartment.get(field), max)
        );
    }

    public Specification<ApartmentEntity> areaBetween(Float min, Float max) {
        return between(min, max, "area");
    }

    public Specification<ApartmentEntity> priceBetween(Float min, Float max) {
        return between(min, max, "price");
    }

    public Specification<ApartmentEntity> hasParking(Boolean hasParking) {
        return (apartment, cq, cb) -> hasParking ? cb.isTrue(apartment.get("hasParking")) : null;
    }
}
