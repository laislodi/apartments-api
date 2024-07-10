package com.rentals.apartment.repositories;

import com.rentals.apartment.domain.ApartmentEntity;
import com.rentals.apartment.domain.ApartmentFilter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class ApartmentRepositoryCustomImpl implements ApartmentRepositoryCustom {
    EntityManager em;

    public ApartmentRepositoryCustomImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<ApartmentEntity> filter(ApartmentFilter filter) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<ApartmentEntity> cq = cb.createQuery(ApartmentEntity.class);

        Root<ApartmentEntity> apartment = cq.from(ApartmentEntity.class);
        List<Predicate> predicates = new ArrayList<>();

        if (filter == null) {
            return em.createQuery(cq).getResultList();
        }

        if (Objects.nonNull(filter.description())) {
            String description = filter.description().toLowerCase();
            if (description.contains("\\[\\]\\|\\{\\}")) {
                System.out.println(description);
            }
            predicates.add(cb.like(cb.lower(apartment.get("description")), filter.description()));
        }
        if (Objects.nonNull(filter.bedrooms())) {
            predicates.add(getBedroomsGreaterOrEqualTo(cb, apartment, filter));
        }
        if (Objects.nonNull(filter.bathrooms())) {
            predicates.add(getBathroomsGreaterOrEqualTo(cb, apartment, filter));
        }
        predicates.add(getAreaBetween(cb, apartment, filter));
        predicates.add(getPriceBetween(cb, apartment, filter));

        if (Objects.nonNull(filter.hasParking()) && filter.hasParking()) {
           predicates.add(cb.isTrue(apartment.get("hasParking")));
        }
        cq.where(predicates.toArray(new Predicate[0]));

        return em.createQuery(cq).getResultList();
    }

    private Predicate getBetween(CriteriaBuilder cb, Float minimum, Float maximum, Expression<Float> filterField) {
        return cb.and(cb.greaterThanOrEqualTo(filterField, minimum), cb.lessThanOrEqualTo(filterField, maximum));
    }

    private Predicate getPriceBetween(CriteriaBuilder cb, Root<ApartmentEntity> apartment, ApartmentFilter filter) {
        Float maxPrice = filter.maxPrice() != null ? filter.maxPrice() : Float.MAX_VALUE;
        Float minPrice = filter.minPrice() != null ? filter.minArea() : 0f;
        return getBetween(cb, minPrice, maxPrice, apartment.get("price"));
    }

    private Predicate getAreaBetween(CriteriaBuilder cb, Root<ApartmentEntity> apartment, ApartmentFilter filter) {
        Float maxArea = filter.maxArea() != null ? filter.maxArea() : Float.MAX_VALUE;
        Float minArea = filter.minArea() != null ? filter.minArea() : 0f;
        return getBetween(cb, minArea, maxArea, apartment.get("area"));
    }

    private Predicate getGreaterOrEqualTo(CriteriaBuilder cb, Expression<Integer> apartmentField, String filterField) {
        boolean greaterThan = filterField.contains("+");
        Integer value = Integer.parseInt(filterField.replace("+", ""));
        if (greaterThan) {
            return cb.greaterThanOrEqualTo(apartmentField, value);
        }
        return cb.equal(apartmentField, value);
    }

    private Predicate getBedroomsGreaterOrEqualTo(CriteriaBuilder cb, Root<ApartmentEntity> apartment, ApartmentFilter filter) {
        return getGreaterOrEqualTo(cb, apartment.get("numberOfBedrooms"), filter.bedrooms());
    }

    private Predicate getBathroomsGreaterOrEqualTo (CriteriaBuilder cb, Root<ApartmentEntity> apartment, ApartmentFilter filter) {
        return getGreaterOrEqualTo(cb, apartment.get("numberOfBathrooms"), filter.bathrooms());
    }
}
