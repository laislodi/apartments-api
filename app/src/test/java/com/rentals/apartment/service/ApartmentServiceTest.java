package com.rentals.apartment.service;

import com.rentals.apartment.domain.ApartmentEntity;
import com.rentals.apartment.repositories.ApartmentRepository;
import com.rentals.apartment.repositories.ApartmentRepositoryCustom;
import com.rentals.apartment.repositories.specifications.ApartmentSpecifications;
import org.hibernate.ObjectNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ApartmentServiceTest {

    private final ApartmentRepository apartmentRepository = Mockito.mock(ApartmentRepository.class);
    private final ApartmentRepositoryCustom apartmentRepositoryCustom = Mockito.mock(ApartmentRepositoryCustom.class);
    private final ApartmentSpecifications specifications = Mockito.mock(ApartmentSpecifications.class);
    private final ApartmentService apartmentService = new ApartmentService(apartmentRepositoryCustom, apartmentRepository, specifications);

    @Test

    void shouldThrowException_whenApartmentNotFound_editApartment() {
        // given - instantiations
        String id = "1234";
        ApartmentEntity entity = new ApartmentEntity();
        Mockito.when(apartmentRepository.findById(id)).thenReturn(Optional.empty());

        // then - assert
        Assertions.assertThrows(ObjectNotFoundException.class, () ->
                // when - call
                apartmentService.editApartment(id, entity)
        );
    }

    @Test
    void shouldCallRepository_whenAllFieldsAreGiven_editApartment() {
        // given - instantiations
        String id = "1234";
        ApartmentEntity inputEntity = new ApartmentEntity();
        inputEntity.setNumberOfBedrooms(2);
        inputEntity.setNumberOfBathrooms(1);
        inputEntity.setDescription("1234 48372 232398 12829");
        inputEntity.setArea(75.3);
        inputEntity.setPrice(1500.0f);
        inputEntity.setHasParking(false);

        ApartmentEntity dbEntity = new ApartmentEntity();
        dbEntity.setId("1234");
        dbEntity.setNumberOfBedrooms(3);
        dbEntity.setNumberOfBathrooms(2);
        dbEntity.setDescription("ertrnv nb ehghyghne kdmccn 3 dhdhd");
        dbEntity.setArea(80.6);
        dbEntity.setPrice(2000.0f);
        dbEntity.setHasParking(true);

        Mockito.when(apartmentRepository.findById(id)).thenReturn(Optional.of(dbEntity));

        ApartmentEntity savedEntity = new ApartmentEntity();
        savedEntity.setId(id);
        savedEntity.setNumberOfBedrooms(2);
        savedEntity.setNumberOfBathrooms(1);
        savedEntity.setDescription("1234 48372 232398 12829");
        savedEntity.setArea(75.3);
        savedEntity.setPrice(1500.0f);
        savedEntity.setHasParking(false);

        // when - call
        apartmentService.editApartment(id, inputEntity);

        // then - assert
        Mockito.verify(apartmentRepository).save(savedEntity);
    }

    @Test
    void shouldCallRepository_whenSomeFieldsAreGiven_editApartment() {
        // given - instantiations
        String id = "1234";
        ApartmentEntity inputEntity = new ApartmentEntity();
        inputEntity.setNumberOfBedrooms(2);
        inputEntity.setArea(75.3);
        inputEntity.setHasParking(false);

        ApartmentEntity dbEntity = new ApartmentEntity();
        dbEntity.setId("1234");
        dbEntity.setNumberOfBedrooms(3);
        dbEntity.setNumberOfBathrooms(2);
        dbEntity.setDescription("ertrnv nb ehghyghne kdmccn 3 dhdhd");
        dbEntity.setArea(80.6);
        dbEntity.setPrice(2000.0f);
        dbEntity.setHasParking(true);

        Mockito.when(apartmentRepository.findById(id)).thenReturn(Optional.of(dbEntity));

        ApartmentEntity savedEntity = new ApartmentEntity();
        savedEntity.setId("1234");
        savedEntity.setNumberOfBedrooms(2);
        savedEntity.setNumberOfBathrooms(2);
        savedEntity.setDescription("ertrnv nb ehghyghne kdmccn 3 dhdhd");
        savedEntity.setArea(75.3);
        savedEntity.setPrice(2000.0f);
        savedEntity.setHasParking(false);

        // when - call
        apartmentService.editApartment(id, inputEntity);

        // then - assert
        Mockito.verify(apartmentRepository).save(savedEntity);
    }

    @Test
    void shouldCallRepository_whenNoneFieldsAreGiven_editApartment() {
        // given - instantiations
        String id = "1234";
        ApartmentEntity inputEntity = new ApartmentEntity();

        ApartmentEntity dbEntity = new ApartmentEntity();
        dbEntity.setId("1234");
        dbEntity.setNumberOfBedrooms(3);
        dbEntity.setNumberOfBathrooms(2);
        dbEntity.setDescription("ertrnv nb ehghyghne kdmccn 3 dhdhd");
        dbEntity.setArea(80.6);
        dbEntity.setPrice(2000.0f);
        dbEntity.setHasParking(true);

        Mockito.when(apartmentRepository.findById(id)).thenReturn(Optional.of(dbEntity));

        ApartmentEntity savedEntity = new ApartmentEntity();
        savedEntity.setId("1234");
        savedEntity.setNumberOfBedrooms(3);
        savedEntity.setNumberOfBathrooms(2);
        savedEntity.setDescription("ertrnv nb ehghyghne kdmccn 3 dhdhd");
        savedEntity.setArea(80.6);
        savedEntity.setPrice(2000.0f);
        savedEntity.setHasParking(true);

        // when - call
        apartmentService.editApartment(id, inputEntity);

        // then - assert
        Mockito.verify(apartmentRepository).save(savedEntity);
    }
}