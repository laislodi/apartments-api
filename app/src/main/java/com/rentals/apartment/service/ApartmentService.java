package com.rentals.apartment.service;

import java.util.*;

import org.hibernate.ObjectNotFoundException;
import static org.springframework.data.jpa.domain.Specification.where;

import org.springframework.data.jpa.domain.Specification;
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
        List<ApartmentEntity> allApartments;
        if (Objects.nonNull(filter)) {
            allApartments = apartmentRepositoryCustom.filter(filter);
        } else {
            Iterable<ApartmentEntity> all = apartmentRepository.findAll();
            allApartments = new ArrayList<>();
            all.forEach(allApartments::add);
        }
        List<ApartmentDTO> allRecords = new ArrayList<>();
        for (ApartmentEntity bean:
                allApartments) {
            allRecords.add(bean.toDTO());
        }
        return allRecords;
    }

    // Change list to Page
    public List<ApartmentDTO> getAllApartmentsWithSpecifications(String orderBy, ApartmentFilter filter) {
        List<ApartmentEntity> allApartments;
        if (Objects.nonNull(filter)) {
            Specification<ApartmentEntity> specification = where(specifications.hasParking(false));
            if (Objects.nonNull(filter.bedrooms())) {
                specification = specification.and(specifications.bedroomsEqualOrGreaterThan(filter.bedrooms()));
            }
            if (Objects.nonNull(filter.bathrooms())) {
                specification = specification.and(specifications.bathroomsEqualOrGreaterThan(filter.bathrooms()));
            }
            if (Objects.nonNull(filter.maxArea())) {
                if (Objects.nonNull(filter.minArea())) {
                    specification = specification.and(specifications.areaBetween(filter.minArea(), filter.maxArea()));
                } else {
                    specification = specification.and(specifications.areaBetween(0F, filter.maxArea()));
                }
            } else if (Objects.nonNull(filter.minArea())) {
                specification = specification.and(specifications.areaBetween(filter.minArea(), Float.MAX_VALUE));
            }
            if (Objects.nonNull(filter.maxPrice())) {
                if (Objects.nonNull(filter.minPrice())) {
                    specification = specification.and(specifications.priceBetween(filter.minPrice(), filter.maxPrice()));
                } else {
                    specification = specification.and(specifications.priceBetween(0F, filter.maxPrice()));
                }
            } else if (Objects.nonNull(filter.minPrice())) {
                specification = specification.and(specifications.priceBetween(filter.minPrice(), Float.MAX_VALUE));
            }
            if (Objects.nonNull(filter.hasParking())) {
                specification = specification.and(specifications.hasParking(filter.hasParking()));
            }
            if (Objects.nonNull(filter.description())) {
                specification = specification.and(specifications.descriptionContains(filter.description()));
            }
            allApartments = apartmentRepository.findAll(specification);
        } else {
            Iterable<ApartmentEntity> all = apartmentRepository.findAll();
            allApartments = new ArrayList<>();
            all.forEach(allApartments::add);
        }
        // TODO: Use Mapstruct to convert entity to DTO
        List<ApartmentDTO> allRecords = new ArrayList<>();
        for (ApartmentEntity bean:
                allApartments) {
            allRecords.add(bean.toDTO());
        }
        return allRecords;
    }

    public ApartmentDTO getApartmentById(String id) throws ObjectNotFoundException {
        Optional<ApartmentEntity> apartment = apartmentRepository.findById(id);
        if (apartment.isEmpty()) {
            throw new ObjectNotFoundException(apartment, "Apartment not found: id: %s".formatted(id));
        }
        return apartment.get().toDTO();
    }

    public ApartmentDTO createApartment(ApartmentEntity apartment) {
        UUID uuid = UUID.randomUUID();
        assertPositiveValues(apartment);
        apartment.setId(uuid.toString());
        ApartmentEntity newApartment = apartmentRepository.save(apartment);
        return newApartment.toDTO();
    }

    private static void assertPositiveValues(ApartmentEntity apartment) {
        if (apartment.getNumberOfBedrooms() < 0 || apartment.getNumberOfBathrooms() < 0) {
            throw new RuntimeException("The number of bedrooms and bathrooms cannot not be negative.");
        }
        if (apartment.getArea() <= 0) {
            throw new RuntimeException("The apartment area cannot not be negative.");
        }
        if (apartment.getPrice() <= 0) {
            throw new RuntimeException("The apartment price cannot not be negative.");
        }
    }

    // Try unit testing this
    // use assert -> verify
    public ApartmentDTO editApartment(String id, ApartmentEntity apartmentEntity) {
        assertPositiveValues(apartmentEntity);
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

       return apartmentRepository.save(apartment).toDTO();
    }

    public void deleteApartment(String id) {
        apartmentRepository.deleteById(id);
    }
}
