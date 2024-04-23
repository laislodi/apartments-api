package com.rentals.apartment.service;

import com.rentals.apartment.domain.ApartmentEntity;
import com.rentals.apartment.domain.ApartmentFilter;
import com.rentals.apartment.domain.ApartmentDTO;
import com.rentals.apartment.repositories.ApartmentRepository;
import com.rentals.apartment.repositories.ApartmentRepositoryCustom;
import com.rentals.apartment.repositories.specifications.ApartmentSpecifications;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

import static org.springframework.data.jpa.domain.Specification.where;

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


    public ApartmentDTO getApartmentById(String id) throws Exception {
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
