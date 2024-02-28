package com.rentals.apartment.repositories;

import com.rentals.apartment.domain.ApartmentBean;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ApartmentRepository extends CrudRepository<ApartmentBean, String>, JpaSpecificationExecutor<ApartmentBean> {

    // Some examples of queries:

    // Order By
    List<ApartmentBean> getApartmentBeansByAreaGreaterThanOrderByNumberOfBedrooms(Double area);

    // Filtering by number of bedrooms
    List<ApartmentBean> findByNumberOfBedrooms(int numberOfBedrooms);
    List<ApartmentBean> findByNumberOfBedroomsLessThanEqual(int numberOfBedrooms);
    List<ApartmentBean> findByNumberOfBedroomsGreaterThanEqual(int numberOfBedrooms);

    // Filtering by Bathrooms
    List<ApartmentBean> findByNumberOfBedroomsAndNumberOfBathrooms(int numberOfBedrooms, int numberOfBathrooms);
    List<ApartmentBean> findByNumberOfBedroomsLessThanAndNumberOfBathroomsAndPriceLessThanEqual(int numberOfBedrooms, int numberOfBathrooms, float price);
    List<ApartmentBean> findByNumberOfBedroomsGreaterThanAndNumberOfBathroomsAndPriceGreaterThanEqual(int numberOfBedrooms, int numberOfBathrooms, float price);
    List<ApartmentBean> findByNumberOfBedroomsAndNumberOfBathroomsAndPriceLessThanEqual(int numberOfBedrooms, int numberOfBathrooms, float price);
    List<ApartmentBean> findByNumberOfBedroomsLessThanEqualAndNumberOfBathroomsLessThanEqual(int numberOfBedrooms, int numberOfBathrooms);
    List<ApartmentBean> findByNumberOfBedroomsGreaterThanEqualAndNumberOfBathroomsLessThanEqual(int numberOfBedrooms, int numberOfBathrooms);
    List<ApartmentBean> findByNumberOfBedroomsLessThanEqualAndNumberOfBathroomsGreaterThanEqual(int numberOfBedrooms, int numberOfBathrooms);
    List<ApartmentBean> findByNumberOfBedroomsGreaterThanEqualAndNumberOfBathroomsGreaterThanEqual(int numberOfBedrooms, int numberOfBathrooms);

    // Filtering by has Parking
    List<ApartmentBean> findByNumberOfBedroomsAndNumberOfBathroomsAndHasParkingAndPriceLessThanEqual(int numberOfBedrooms, int numberOfBathrooms, boolean hasParking, float price);


    // Filtering by Price
    List<ApartmentBean> findByPriceLessThan(double price);
    List<ApartmentBean> findByPriceGreaterThanEqual(double price);
    List<ApartmentBean> findByNumberOfBedroomsGreaterThanEqualAndNumberOfBathroomsGreaterThanEqualAndPriceLessThan(int numberOfBedrooms, int numberOfBathrooms, float price);

    // Filtering by Description
    List<ApartmentBean> findAllByDescriptionContainingIgnoreCase(String description);
}