package com.rentals.apartment.repositories;

import com.rentals.apartment.domain.ApartmentEntity;
import com.rentals.apartment.domain.ApartmentFilter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

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

        String description = filter.description();
        if (description.contains("\\[\\]\\|\\{\\}")) {
            System.out.println(description);
        }

        predicates.add(cb.like(apartment.get("description"), filter.description()));
        predicates.add(getBedroomsGreaterOrEqualTo(cb, apartment, filter));
        predicates.add(getBathroomsGreaterOrEqualTo(cb, apartment, filter));
        predicates.add(getAreaBetween(cb, apartment, filter));
        predicates.add(getPriceBetween(cb, apartment, filter));

        if (filter.hasParking()) {
           predicates.add(cb.isTrue(apartment.get("hasParking")));
        }

        cq.where(predicates.toArray(new Predicate[0]));

        return em.createQuery(cq).getResultList();
    }

    private Predicate getPriceBetween(CriteriaBuilder cb, Root<ApartmentEntity> apartment, ApartmentFilter filter) {
        Float maxPrice = filter.maxPrice() != null ? filter.maxPrice() : Float.MAX_VALUE;
        Float minPrice = filter.minPrice() != null ? filter.minArea() : 0f;
        Expression<Float> area = apartment.get("price");
        return cb.and(cb.greaterThanOrEqualTo(area, minPrice), cb.lessThanOrEqualTo(area, maxPrice));
    }

    private Predicate getAreaBetween(CriteriaBuilder cb, Root<ApartmentEntity> apartment, ApartmentFilter filter) {
        Float maxArea = filter.maxArea() != null ? filter.maxArea() : Float.MAX_VALUE;
        Float minArea = filter.minArea() != null ? filter.minArea() : 0f;
        Expression<Float> area = apartment.get("area");
        return cb.and(cb.greaterThanOrEqualTo(area, minArea), cb.lessThanOrEqualTo(area, maxArea));
    }

    private Predicate getGreaterOrEqualTo(CriteriaBuilder cb, Root<ApartmentEntity> apartment, ApartmentFilter filter, String field ) {
        String bedrooms = filter.bedrooms();
        Expression<Integer> numberOfBedrooms = apartment.get(field);
        boolean greaterThan = bedrooms.contains("+");
        Integer value = Integer.parseInt(bedrooms.replace("+", ""));
        if (greaterThan) {
            return cb.greaterThanOrEqualTo(numberOfBedrooms, value);
        }
        return cb.equal(numberOfBedrooms, value);
    }

    private Predicate getBedroomsGreaterOrEqualTo(CriteriaBuilder cb, Root<ApartmentEntity> apartment, ApartmentFilter filter) {
        return getGreaterOrEqualTo(cb, apartment, filter, "numberOfBedrooms");
    }

    private Predicate getBathroomsGreaterOrEqualTo (CriteriaBuilder cb, Root<ApartmentEntity> apartment, ApartmentFilter filter) {
        return getGreaterOrEqualTo(cb, apartment, filter, "numberOfBathrooms");
    }
}
