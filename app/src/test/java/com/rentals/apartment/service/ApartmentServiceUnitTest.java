package com.rentals.apartment.service;

import com.rentals.apartment.domain.ApartmentEntity;
import com.rentals.apartment.repositories.ApartmentRepository;
import com.rentals.apartment.repositories.ApartmentRepositoryCustom;
import com.rentals.apartment.repositories.specifications.ApartmentSpecifications;
import org.hibernate.ObjectNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

class ApartmentServiceUnitTest {

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
        ApartmentEntity inputEntity = new ApartmentEntity()
                .setNumberOfBedrooms(2)
                .setNumberOfBathrooms(1)
                .setDescription("1234 48372 232398 12829")
                .setArea(75.3)
                .setPrice(1500.0f)
                .setHasParking(false);

        ApartmentEntity dbEntity = new ApartmentEntity()
                .setId(id)
                .setNumberOfBedrooms(3)
                .setNumberOfBathrooms(2)
                .setDescription("ertrnv nb ehghyghne kdmccn 3 dhdhd")
                .setArea(80.6)
                .setPrice(2000.0f)
                .setHasParking(true);

        Mockito.when(apartmentRepository.findById(id)).thenReturn(Optional.of(dbEntity));

        ApartmentEntity savedEntity = new ApartmentEntity()
                .setId(id)
                .setNumberOfBedrooms(2)
                .setNumberOfBathrooms(1)
                .setDescription("1234 48372 232398 12829")
                .setArea(75.3)
                .setPrice(1500.0f)
                .setHasParking(false);

        Mockito.when(apartmentRepository.save(Mockito.any())).thenReturn(savedEntity);

        // when - call
        apartmentService.editApartment(id, inputEntity);

        // then - assert
        Mockito.verify(apartmentRepository).save(savedEntity);
    }

    @Test
    void shouldCallRepository_whenSomeFieldsAreGiven_editApartment() {
        // given - instantiations
        String id = "1234";
        ApartmentEntity inputEntity = new ApartmentEntity()
                .setNumberOfBedrooms(2)
                .setArea(75.3)
                .setHasParking(false);

        ApartmentEntity dbEntity = new ApartmentEntity()
                .setId("1234")
                .setNumberOfBedrooms(3)
                .setNumberOfBathrooms(2)
                .setDescription("ertrnv nb ehghyghne kdmccn 3 dhdhd")
                .setArea(80.6)
                .setPrice(2000.0f)
                .setHasParking(true);

        Mockito.when(apartmentRepository.findById(id)).thenReturn(Optional.of(dbEntity));

        ApartmentEntity savedEntity = new ApartmentEntity()
                .setId("1234")
                .setNumberOfBedrooms(2)
                .setNumberOfBathrooms(2)
                .setDescription("ertrnv nb ehghyghne kdmccn 3 dhdhd")
                .setArea(75.3)
                .setPrice(2000.0f)
                .setHasParking(false);

        Mockito.when(apartmentRepository.save(Mockito.any())).thenReturn(savedEntity);

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

        ApartmentEntity dbEntity = new ApartmentEntity()
                .setId("1234")
                .setNumberOfBedrooms(3)
                .setNumberOfBathrooms(2)
                .setDescription("ertrnv nb ehghyghne kdmccn 3 dhdhd")
                .setArea(80.6)
                .setPrice(2000.0f)
                .setHasParking(true);

        Mockito.when(apartmentRepository.findById(id)).thenReturn(Optional.of(dbEntity));

        ApartmentEntity savedEntity = new ApartmentEntity()
                .setId("1234")
                .setNumberOfBedrooms(3)
                .setNumberOfBathrooms(2)
                .setDescription("ertrnv nb ehghyghne kdmccn 3 dhdhd")
                .setArea(80.6)
                .setPrice(2000.0f)
                .setHasParking(true);

        Mockito.when(apartmentRepository.save(Mockito.any())).thenReturn(savedEntity);

        // when - call
        apartmentService.editApartment(id, inputEntity);

        // then - assert
        Mockito.verify(apartmentRepository).save(savedEntity);
    }
}