package com.rentals.apartment.controller;

import com.rentals.apartment.domain.ApartmentDTO;
import com.rentals.apartment.domain.ApartmentEntity;
import com.rentals.apartment.domain.ApartmentFilter;
import com.rentals.apartment.repositories.ApartmentRepository;
import com.rentals.apartment.repositories.ApartmentRepositoryCustom;
import com.rentals.apartment.repositories.specifications.ApartmentSpecifications;
import com.rentals.apartment.service.ApartmentService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Testcontainers
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("ApartmentControllerE2eTest: End to end tests with apartment controller")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ApartmentsControllerE2eTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16.1");

    @Autowired
    ApartmentRepository apartmentRepository;
    @Autowired
    ApartmentRepositoryCustom apartmentRepositoryCustom;
    @Autowired
    ApartmentsController apartmentsController;

    @BeforeEach
    void setUp() {
        ApartmentSpecifications apartmentSpecifications = new ApartmentSpecifications();
        postgres.start();
        ApartmentService apartmentService = new ApartmentService(apartmentRepositoryCustom, apartmentRepository, apartmentSpecifications);
        apartmentsController = new ApartmentsController(apartmentService);
    }

    @Test
    void connectionEstablished() {
        Assertions.assertTrue(postgres.isCreated());
        Assertions.assertTrue(postgres.isRunning());
    }

    @Test
    void getAllApartmentsWithSpecifications() {
    }

    @Test
    void getAllApartments() {
        ApartmentFilter filter = new ApartmentFilter("1+", "1+", 0F, 999F, 0F, 999F, false, "");
        ResponseEntity<List<ApartmentDTO>> response = apartmentsController.getAllApartments(filter, "ASC");
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getApartment() {
    }

    @Test
    void createApartment() {
        // e2e tests:
        // token = restTemplate... login(usuario que ja ta no bd, senha)
        // restTemplate.createApartment(token)
        ApartmentEntity apartment = new ApartmentEntity()
                .setNumberOfBedrooms(2)
                .setNumberOfBathrooms(2)
                .setArea(52.1)
                .setPrice(2800.0F)
                .setHasParking(true)
                .setDescription("Quis non etiam nisl in aliquam potenti ac lorem, odio suspendisse netus auctor finibus pharetra mus placerat");
        ResponseEntity<ApartmentDTO> response = apartmentsController.createApartment(apartment);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        assertEquals(2, response.getBody().numberOfBedrooms());
        assertEquals(2, response.getBody().numberOfBathrooms());
        assertEquals(52.1, response.getBody().area());
        assertEquals(2800.0F, response.getBody().price());
        assertEquals(true, response.getBody().hasParking());
        assertEquals("Quis non etiam nisl in aliquam potenti ac lorem, odio suspendisse netus auctor finibus pharetra mus placerat",
                response.getBody().description());
    }

    @Test
    void editApartment() {
        ApartmentEntity apartment = new ApartmentEntity()
                .setNumberOfBedrooms(2)
                .setNumberOfBathrooms(2)
                .setArea(68.0)
                .setDescription("Ut litora tellus nisi a ridiculus orci praesent penatibus blandit cubilia arcu at ipsum");

        ResponseEntity<ApartmentDTO> response = apartmentsController.editApartment("F1rsT-@p4rtm3nt", apartment.toDTO());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().numberOfBedrooms());
        assertEquals(2, response.getBody().numberOfBathrooms());
        assertEquals(68.0, response.getBody().area());
        assertEquals("Ut litora tellus nisi a ridiculus orci praesent penatibus blandit cubilia arcu at ipsum",
                response.getBody().description());
    }

    @Test
    void shouldEditApartment() {
        ApartmentEntity apartment = new ApartmentEntity()
                .setNumberOfBedrooms(2)
                .setNumberOfBathrooms(2)
                .setArea(68.0)
                .setDescription("Ut litora tellus nisi a ridiculus orci praesent penatibus blandit cubilia arcu at ipsum");
        ResponseEntity<ApartmentDTO> response = apartmentsController.editApartment("F1rsT-@p4rtm3nt", apartment.toDTO());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        // Should not change
        assertEquals(false, response.getBody().hasParking());
        assertEquals(1580.0F, response.getBody().price());

        // Should have changed according to the values above
        assertEquals(2, response.getBody().numberOfBedrooms());
        assertEquals(2, response.getBody().numberOfBathrooms());
        assertEquals(68.0, response.getBody().area());
        assertEquals("Ut litora tellus nisi a ridiculus orci praesent penatibus blandit cubilia arcu at ipsum",
                response.getBody().description());
    }
}