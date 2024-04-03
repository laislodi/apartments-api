package com.rentals.apartment.repositories;

import com.rentals.apartment.domain.ApartmentEntity;
import com.rentals.apartment.domain.ApartmentFilter;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApartmentRepositoryCustom {
    List<ApartmentEntity> filter(ApartmentFilter filter);
}
