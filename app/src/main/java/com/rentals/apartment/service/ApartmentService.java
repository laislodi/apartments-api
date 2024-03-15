package com.rentals.apartment.service;

import com.rentals.apartment.domain.ApartmentEntity;
import com.rentals.apartment.domain.ApartmentFilter;
import com.rentals.apartment.domain.ApartmentDTO;
import com.rentals.apartment.repositories.ApartmentRepository;
import com.rentals.apartment.repositories.ApartmentSpecifications;
import org.springframework.stereotype.Service;

import java.util.*;

import static org.springframework.data.jpa.domain.Specification.where;

@Service
public class ApartmentService {

    private final ApartmentRepository apartmentRepository;
    private final ApartmentSpecifications specifications;

    public ApartmentService(ApartmentRepository apartmentRepository, ApartmentSpecifications specifications) {
        this.apartmentRepository = apartmentRepository;
        this.specifications = specifications;
    }

    public List<ApartmentDTO> getAllApartments(String orderBy, ApartmentFilter filter) {
        List<ApartmentEntity> allApartments;
        allApartments = apartmentRepository.findAll(
                where(specifications.descriptionContains(filter.getDescription()))
                        .and(specifications.areaBetween(filter.getMinArea(), filter.getMaxArea()))
                        .and(specifications.priceBetween(filter.getMinPrice(), filter.getMaxPrice()))
                        .and(specifications.hasParking(filter.getHasParking()))
                        .and(specifications.bedroomsEqualOrGreaterThan(filter.getBedrooms()))
                        .and(specifications.bathroomsEqualOrGreaterThan(filter.getBathrooms()))
                        );
        // TODO: Use Mapstruct to convert entity to DTO
        List<ApartmentDTO> allRecords = new ArrayList<>();
        for (ApartmentEntity bean:
                allApartments) {
            allRecords.add(bean.toRecord());
        }
        return allRecords;
    }


    public ApartmentDTO getApartmentById(String id) throws Exception {
        Optional<ApartmentEntity> apartment = apartmentRepository.findById(id);
        if (apartment.isEmpty()) {
            throw new Exception("Apartment not found: id: %s".formatted(id));
        }
        return apartment.get().toRecord();
    }

    public ApartmentDTO createApartment(ApartmentEntity apartment) {
        UUID uuid = UUID.randomUUID();
        apartment.setId(uuid.toString());
        ApartmentEntity newApartment = apartmentRepository.save(apartment);
        return newApartment.toRecord();
    }

    public ApartmentEntity editApartment(String id, ApartmentEntity apartmentEntity) {
        ApartmentEntity apartment = apartmentRepository.findById(id).get();
        ApartmentEntity newApartment = new ApartmentEntity();
        newApartment.setNumberOfBedrooms(apartment.getNumberOfBedrooms());
        newApartment.setNumberOfBathrooms(apartment.getNumberOfBathrooms());
        newApartment.setArea(apartment.getArea());
        newApartment.setHasParking(apartment.getHasParking());
        newApartment.setPrice(apartment.getPrice());
        newApartment.setDescription(apartment.getDescription());

        newApartment.setId(id);
        newApartment.setNumberOfBedrooms(apartmentEntity.getNumberOfBedrooms());
        newApartment.setNumberOfBathrooms(apartmentEntity.getNumberOfBathrooms());
        newApartment.setArea(apartmentEntity.getArea());
        newApartment.setHasParking(apartmentEntity.getHasParking());
        newApartment.setPrice(apartmentEntity.getPrice());
        newApartment.setDescription(apartmentEntity.getDescription());

       return apartmentRepository.save(newApartment);
    }

}
