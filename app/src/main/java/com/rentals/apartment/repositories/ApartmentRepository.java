package com.rentals.apartment.repositories;


import com.rentals.apartment.domain.ApartmentEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ApartmentRepository extends CrudRepository<ApartmentEntity, String>, JpaSpecificationExecutor<ApartmentEntity> {
}
