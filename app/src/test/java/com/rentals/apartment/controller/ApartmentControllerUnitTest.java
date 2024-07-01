package com.rentals.apartment.controller;

import com.rentals.apartment.domain.ApartmentDTO;
import com.rentals.apartment.domain.ApartmentEntity;
import com.rentals.apartment.domain.ApartmentFilter;
import com.rentals.apartment.repositories.ApartmentRepository;
import com.rentals.apartment.repositories.ApartmentRepositoryCustom;
import com.rentals.apartment.repositories.specifications.ApartmentSpecifications;
import com.rentals.apartment.service.ApartmentService;
import org.hibernate.ObjectNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ApartmentControllerUnitTest {

    ApartmentRepositoryCustom apartmentRepositoryCustom = Mockito.mock(ApartmentRepositoryCustom.class);
    ApartmentRepository apartmentRepository = Mockito.mock(ApartmentRepository.class);
    ApartmentSpecifications apartmentSpecifications = Mockito.mock(ApartmentSpecifications.class);
    ApartmentService apartmentService = new ApartmentService(apartmentRepositoryCustom, apartmentRepository, apartmentSpecifications);
    ApartmentsController apartmentsController = new ApartmentsController(apartmentService);
    ApartmentFilter apartmentFilter = new ApartmentFilter("1+", "1+", 0f, 999f, 0f, 999f, false, "");

    static List<ApartmentEntity> allApartments = new ArrayList<>();
    static List<ApartmentDTO> allApartmentsDTO = new ArrayList<>();

    @BeforeAll
    static void beforeAll() {
        ApartmentEntity apartment1 = new ApartmentEntity()
                .setId("F1rsT-@p4rtm3nt")
                .setNumberOfBedrooms(2)
                .setNumberOfBathrooms(1)
                .setArea(45.3)
                .setPrice(1500.0F)
                .setHasParking(false)
                .setDescription("lorem ipsum dolor sit amet consectetur adipiscing elit montes ad habitant");

        ApartmentEntity apartment2 = new ApartmentEntity()
                .setId("S3c0nd-@p4rtm3nt")
                .setNumberOfBedrooms(2)
                .setNumberOfBathrooms(2)
                .setArea(55.3)
                .setPrice(2000.0F)
                .setHasParking(true)
                .setDescription("lorem ipsum dolor sit amet consectetur adipiscing elit montes ad habitant");

        ApartmentEntity apartment3 = new ApartmentEntity()
                .setId("Th1rd-@p4rtm3nt")
                .setNumberOfBedrooms(3)
                .setNumberOfBathrooms(2)
                .setArea(64.3)
                .setPrice(2500.0F)
                .setHasParking(true)
                .setDescription("lorem ipsum dolor sit amet consectetur adipiscing elit montes ad habitant");

        allApartments.add(apartment1);
        allApartments.add(apartment2);
        allApartments.add(apartment3);

        ApartmentDTO apartmentDTO1 = new ApartmentDTO("F1rsT-@p4rtm3nt", 2, 1, 45.3,
                false, 1500.0F, "lorem ipsum dolor sit amet consectetur adipiscing elit montes ad habitant");
        ApartmentDTO apartmentDTO2 = new ApartmentDTO("S3c0nd-@p4rtm3nt", 2, 2, 55.3,
                true, 2000.0F, "lorem ipsum dolor sit amet consectetur adipiscing elit montes ad habitant");
        ApartmentDTO apartmentDTO3 = new ApartmentDTO("Th1rd-@p4rtm3nt", 3, 2, 64.3,
                true, 2500.0F, "lorem ipsum dolor sit amet consectetur adipiscing elit montes ad habitant");

        allApartmentsDTO.add(apartmentDTO1);
        allApartmentsDTO.add(apartmentDTO2);
        allApartmentsDTO.add(apartmentDTO3);
    }

    @Test
    void shouldShowAllApartmentsSuccessfully() {
        // Given
        Mockito.when(apartmentRepositoryCustom.filter(apartmentFilter)).thenReturn(allApartments);

        // When
        ResponseEntity<List<ApartmentDTO>> response = apartmentsController.getAllApartments(apartmentFilter, "ASC");

        // Then
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(allApartmentsDTO, response.getBody());
        Assertions.assertEquals(3, response.getBody().size());
    }

    @Test
    void shouldFilterApartmentsByParking() {
        ApartmentFilter apartmentFilter = new ApartmentFilter("1+", "1+", 0f, 999f, 0f, 999f, true, "");

        // Given
        List<ApartmentDTO> expectedResult = new ArrayList<>(allApartmentsDTO.subList(1,3));
        List<ApartmentEntity> expected = new ArrayList<>(allApartments.subList(1,3));
        Mockito.when(apartmentRepositoryCustom.filter(apartmentFilter)).thenReturn(expected);

        // When
        ResponseEntity<List<ApartmentDTO>> response = apartmentsController.getAllApartments(apartmentFilter, "ASC");

        // Then
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(expectedResult, response.getBody());
        Assertions.assertEquals(2, response.getBody().size());
        Assertions.assertEquals(allApartmentsDTO.get(1), response.getBody().get(0));
        Assertions.assertEquals(allApartmentsDTO.get(2), response.getBody().get(1));
    }

    @Test
    void shouldFilterApartmentsByNumberOfBathrooms() {
        ApartmentFilter apartmentFilter = new ApartmentFilter("1+", "1", 0f, 999f, 0f, 999f, true, "");

        // Given
        List<ApartmentDTO> expectedResult = new ArrayList<>(allApartmentsDTO.subList(0,1));
        List<ApartmentEntity> expected = new ArrayList<>(allApartments.subList(0,1));
        Mockito.when(apartmentRepositoryCustom.filter(apartmentFilter)).thenReturn(expected);

        // When
        ResponseEntity<List<ApartmentDTO>> response = apartmentsController.getAllApartments(apartmentFilter, "ASC");

        // Then
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(expectedResult, response.getBody());
        Assertions.assertEquals(1, response.getBody().size());
        Assertions.assertEquals(allApartmentsDTO.get(0), response.getBody().get(0));
    }

    @Test
    void shouldGetTheCorrectApartment() {
        // Given
        String id = "F1rsT-@p4rtm3nt";
        String description = "lorem ipsum dolor sit amet consectetur adipiscing elit montes ad habitant";
        ApartmentEntity expected = new ApartmentEntity()
                .setId(id)
                .setNumberOfBedrooms(2)
                .setNumberOfBathrooms(1)
                .setArea(45.3)
                .setPrice(1500.0F)
                .setHasParking(false)
                .setDescription(description);
        ApartmentDTO expectedResult = new ApartmentDTO(id, 2, 1, 45.3, false, 1500F, description);

        Mockito.when(apartmentRepository.findById(id)).thenReturn(Optional.of(expected));

        // When
        ResponseEntity<ApartmentDTO> response = apartmentsController.getApartment(id);

        // Then
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(expectedResult, response.getBody());
    }

    @Test
    void shouldNotGetTheCorrectApartment_expectNotFoundError() {
        String id = "not_a_valid_id";

        // Given
        Mockito.when(apartmentRepository.findById(id)).thenReturn(Optional.empty());

        // When
        ResponseEntity<ApartmentDTO> response = apartmentsController.getApartment(id);

        // Then
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Assertions.assertNull(response.getBody());
    }

    @Test
    void shouldNotGetTheCorrectApartment_expectServerError() {
        String id = "not_a_valid_id";

        // Given
        Mockito.when(apartmentRepository.findById(id)).thenThrow(RuntimeException.class);

        // When
        ResponseEntity<ApartmentDTO> response = apartmentsController.getApartment(id);

        // Then
        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        Assertions.assertNull(response.getBody());
    }

    @Test
    void shouldAddAnApartmentWithoutError() {
        String id = "F0urTh-@p4rtm3nt";
        Integer bedrooms = 1;
        Integer bathrooms = 1;
        Double area = 40.6;
        Float price = 900.0F;
        Boolean hasParking = false;
        String description = "lorem ipsum dolor sit amet consectetur adipiscing elit montes ad habitant";

        ApartmentEntity newApartmentEntity = new ApartmentEntity();
        newApartmentEntity.setId(id);
        newApartmentEntity.setNumberOfBedrooms(bedrooms);
        newApartmentEntity.setNumberOfBathrooms(bathrooms);
        newApartmentEntity.setArea(area);
        newApartmentEntity.setPrice(price);
        newApartmentEntity.setHasParking(hasParking);
        newApartmentEntity.setDescription(description);

        ApartmentDTO apartmentDTO = new ApartmentDTO(id, bedrooms, bathrooms, area, hasParking, price, description);

        Mockito.when(apartmentRepository.save(Mockito.any())).thenReturn(newApartmentEntity);

        // When
        ResponseEntity<ApartmentDTO> response = apartmentsController.createApartment(newApartmentEntity);

        // Then
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(apartmentDTO.numberOfBedrooms(), response.getBody().numberOfBedrooms());
        Assertions.assertEquals(apartmentDTO.numberOfBathrooms(), response.getBody().numberOfBathrooms());
        Assertions.assertEquals(apartmentDTO.area(), response.getBody().area());
        Assertions.assertEquals(apartmentDTO.price(), response.getBody().price());
        Assertions.assertEquals(apartmentDTO.hasParking(), response.getBody().hasParking());
        Assertions.assertEquals(apartmentDTO.description(), response.getBody().description());
    }

    @Test
    void shouldEditAnApartment() {
        String description = "lorem ipsum dolor sit amet consectetur adipiscing elit montes ad habitant";
        String id = "3dTed-@p4rtm3nt";
        ApartmentEntity editedApartment = new ApartmentEntity()
                .setId(id)
                .setNumberOfBedrooms(2)
                .setNumberOfBathrooms(1)
                .setArea(45.3)
                .setPrice(1500.0F)
                .setHasParking(false)
                .setDescription(description);
        ApartmentDTO apartment = new ApartmentDTO(id, 2, 1, 45.3, false, 1500F, description);

        Mockito.when(apartmentRepository.findById(id)).thenReturn(Optional.ofNullable(editedApartment));
        Mockito.when(apartmentRepository.save(Mockito.any())).thenReturn(editedApartment);

        ResponseEntity<ApartmentDTO> response = apartmentsController.editApartment(id, apartment);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(apartment.numberOfBedrooms(), response.getBody().numberOfBedrooms());
        Assertions.assertEquals(apartment.numberOfBathrooms(), response.getBody().numberOfBathrooms());
        Assertions.assertEquals(apartment.area(), response.getBody().area());
        Assertions.assertEquals(apartment.price(), response.getBody().price());
        Assertions.assertEquals(apartment.hasParking(), response.getBody().hasParking());
        Assertions.assertEquals(description, response.getBody().description());
    }

    @Test
    void shouldNotEditAnApartment_ApartmentNotFound() {
        String description = "lorem ipsum dolor sit amet consectetur adipiscing elit montes ad habitant";
        String id = "Invalid_ID";
        ApartmentEntity apartment = new ApartmentEntity();
        apartment.setId(id);
        apartment.setNumberOfBedrooms(2);
        apartment.setNumberOfBathrooms(1);
        apartment.setArea(45.3);
        apartment.setPrice(1800.0F);
        apartment.setHasParking(false);
        apartment.setDescription(description);

        Mockito.when(apartmentRepository.findById(id)).thenReturn(Optional.empty());

        Assertions.assertThrows(ObjectNotFoundException.class,
                () -> apartmentsController.editApartment(id, apartment.toRecord()));
    }
}
