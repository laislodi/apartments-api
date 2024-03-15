package com.rentals.apartment.repositories;

import com.rentals.apartment.domain.ApartmentEntity;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ApartmentSpecifications {

    public Specification<ApartmentEntity> descriptionContains(String description) {
        return (apartment, cq, cb) -> cb.like(apartment.get("description"), "%" + description + "%");
    }

    private Specification<ApartmentEntity> equalOrGreaterThan(String value, String field) {
        return (apartment, cq, cb) -> {
            boolean greaterThan = value.contains("+");
            Integer val = Integer.parseInt(value.replace("+", ""));
            if (greaterThan) {
                return cb.greaterThan(apartment.get(field), val);
            }
            return cb.equal(apartment.get(field), val);
        };
    }

    public Specification<ApartmentEntity> bedroomsEqualOrGreaterThan(String bedrooms) {
        return equalOrGreaterThan(bedrooms, "numberOfBedrooms");
    }

    public Specification<ApartmentEntity> bathroomsEqualOrGreaterThan(String bathrooms) {
        return equalOrGreaterThan(bathrooms, "numberOfBathrooms");
    }

    public Specification<ApartmentEntity> areaBetween(Float min, Float max) {
        if (Objects.isNull(min)) {
            min = 0f;
        }
        if (Objects.isNull(max)) {
            max = Float.MAX_VALUE;
        }
        Float finalMin = min;
        Float finalMax = max;
        return (apartment, cq, cb) -> cb.and(
                cb.greaterThanOrEqualTo(apartment.get("area"), finalMin),
                cb.lessThanOrEqualTo(apartment.get("area"), finalMax)
        );
    }

    public Specification<ApartmentEntity> priceBetween(Float min, Float max) {
        if (Objects.isNull(min)) {
            min = Float.MIN_VALUE;
        }
        if (Objects.isNull(max)) {
            max = Float.MAX_VALUE;
        }
        Float finalMin = min;
        Float finalMax = max;
        return (apartment, cq, cb) -> cb.and(
                cb.greaterThanOrEqualTo(apartment.get("price"), finalMin),
                cb.lessThanOrEqualTo(apartment.get("price"), finalMax)
        );
    }

    public Specification<ApartmentEntity> hasParking(Boolean hasParking) {
        Specification<ApartmentEntity> sp = (apartment, cq, cb) -> {
            Predicate predicate = hasParking ? cb.isTrue(apartment.get("hasParking")) : null;
            return predicate;
        };
        return sp;
    }
}
