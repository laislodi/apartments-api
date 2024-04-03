package com.rentals.apartment.domain;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ApartmentSpecification {
    public static Specification<ApartmentDTO> filter(List<FilterDTO> filterDTOList) {
        return new Specification<ApartmentDTO>() {
            @Override
            public Predicate toPredicate(Root<ApartmentDTO> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                filterDTOList.forEach(filter -> {
                    Predicate predicate = criteriaBuilder.equal(root.get(filter.columnName()), filter.columnValue());
                    predicates.add(predicate);
                });
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}
