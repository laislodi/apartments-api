package com.rentals.apartment.repositories;


import com.rentals.apartment.domain.ApartmentBean;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ApartmentRepository extends CrudRepository<ApartmentBean, String> {
}