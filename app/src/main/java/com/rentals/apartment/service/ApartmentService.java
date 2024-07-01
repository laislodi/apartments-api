package com.rentals.apartment.service;

import java.util.*;

import org.hibernate.ObjectNotFoundException;
import static org.springframework.data.jpa.domain.Specification.where;
import org.springframework.stereotype.Service;

import com.rentals.apartment.domain.ApartmentDTO;
import com.rentals.apartment.domain.ApartmentEntity;
import com.rentals.apartment.domain.ApartmentFilter;
import com.rentals.apartment.repositories.ApartmentRepository;
import com.rentals.apartment.repositories.ApartmentRepositoryCustom;
import com.rentals.apartment.repositories.specifications.ApartmentSpecifications;

@Service
public class ApartmentService {

    private final ApartmentRepository apartmentRepository;
    private final ApartmentRepositoryCustom apartmentRepositoryCustom;
    private final ApartmentSpecifications specifications;

    public ApartmentService(ApartmentRepositoryCustom apartmentRepositoryCustom, ApartmentRepository apartmentRepository, ApartmentSpecifications specifications) {
        this.apartmentRepository = apartmentRepository;
        this.apartmentRepositoryCustom = apartmentRepositoryCustom;
        this.specifications = specifications;
    }

    public List<ApartmentDTO> getAllApartmentsWithCustom(String orderBy, ApartmentFilter filter) {
        List<ApartmentEntity> allApartments = apartmentRepositoryCustom.filter(filter);
        List<ApartmentDTO> allRecords = new ArrayList<>();
        for (ApartmentEntity bean:
                allApartments) {
            allRecords.add(bean.toRecord());
        }
        return allRecords;
    }

    // Change list to Page
    public List<ApartmentDTO> getAllApartmentsWithSpecifications(String orderBy, ApartmentFilter filter) {
        List<ApartmentEntity> allApartments;
        allApartments = apartmentRepository.findAll(
                where(specifications.descriptionContains(filter.description()))
                        .and(specifications.areaBetween(filter.minArea(), filter.maxArea()))
                        .and(specifications.priceBetween(filter.minPrice(), filter.maxPrice()))
                        .and(specifications.hasParking(filter.hasParking()))
                        .and(specifications.bedroomsEqualOrGreaterThan(filter.bedrooms()))
                        .and(specifications.bathroomsEqualOrGreaterThan(filter.bathrooms()))
                        );
        // TODO: Use Mapstruct to convert entity to DTO
        List<ApartmentDTO> allRecords = new ArrayList<>();
        for (ApartmentEntity bean:
                allApartments) {
            allRecords.add(bean.toRecord());
        }
        return allRecords;
    }

    public ApartmentDTO getApartmentById(String id) throws ObjectNotFoundException {
        Optional<ApartmentEntity> apartment = apartmentRepository.findById(id);
        if (apartment.isEmpty()) {
            throw new ObjectNotFoundException(apartment, "Apartment not found: id: %s".formatted(id));
        }
        return apartment.get().toRecord();
    }

    public ApartmentDTO createApartment(ApartmentEntity apartment) {
        UUID uuid = UUID.randomUUID();
        apartment.setId(uuid.toString());
        ApartmentEntity newApartment = apartmentRepository.save(apartment);
        return newApartment.toRecord();
    }

    // Try unit testing this
    // use assert -> verify
    public ApartmentDTO editApartment(String id, ApartmentEntity apartmentEntity) {
        Optional<ApartmentEntity> optional = apartmentRepository.findById(id);
        if (optional.isEmpty()) {
            throw new ObjectNotFoundException(id, ApartmentEntity.class);
        }
        ApartmentEntity apartment = optional.get();

        if (Objects.nonNull(apartmentEntity.getNumberOfBedrooms())) {
            apartment.setNumberOfBedrooms(apartmentEntity.getNumberOfBedrooms());
        }
        if (Objects.nonNull(apartmentEntity.getNumberOfBathrooms())) {
            apartment.setNumberOfBathrooms(apartmentEntity.getNumberOfBathrooms());
        }
        if (Objects.nonNull(apartmentEntity.getPrice())) {
            apartment.setPrice(apartmentEntity.getPrice());
        }
        if (Objects.nonNull(apartmentEntity.getArea())) {
            apartment.setArea(apartmentEntity.getArea());
        }
        if (Objects.nonNull(apartmentEntity.getHasParking())) {
            apartment.setHasParking(apartmentEntity.getHasParking());
        }
        if (Objects.nonNull(apartmentEntity.getDescription())) {
            apartment.setDescription(apartmentEntity.getDescription());
        }

       return apartmentRepository.save(apartment).toRecord();
    }

}
