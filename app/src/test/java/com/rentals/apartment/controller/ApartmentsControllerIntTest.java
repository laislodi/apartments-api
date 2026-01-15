package com.rentals.apartment.controller;

import com.rentals.apartment.domain.*;
import com.rentals.apartment.repositories.ApartmentRepository;
import com.rentals.apartment.repositories.ApartmentRepositoryCustom;
import com.rentals.apartment.repositories.specifications.ApartmentSpecifications;
import com.rentals.apartment.service.ApartmentService;
import org.junit.jupiter.api.*;
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

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("ApartmentControllerIntTest: tests with apartment controller")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ApartmentsControllerIntTest {

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
    @Order(0)
    void connectionEstablished() {
        Assertions.assertTrue(postgres.isCreated());
        Assertions.assertTrue(postgres.isRunning());
    }

    @Test
    @Order(1)
    void getAllApartmentsWithAFilterWithNullFields() {
        ApartmentFilter filter = new ApartmentFilter(null, null, null, null, null, null, null, null);
        ResponseEntity<List<ApartmentDTO>> response = apartmentsController.getAllApartments(filter, "ASC");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(3, response.getBody().size());
    }

    @Test
    @Order(1)
    void getAllApartmentsWithSpecifications() {
        ApartmentFilter filter = new ApartmentFilter("0+", "0+", 0F, 999F, 0F, 9999F, false, "");
        ResponseEntity<List<ApartmentDTO>> response = apartmentsController.getAllApartmentsWithSpecifications(filter, "ASC");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(3, response.getBody().size());
    }

    @Test
    @Order(1)
    void getAllApartmentsWithSpecificationsAndFilterByBedrooms() {
        ApartmentFilter filter = new ApartmentFilter("2", null, null, null, null, null, null, null);
        ResponseEntity<List<ApartmentDTO>> response = apartmentsController.getAllApartmentsWithSpecifications(filter, "ASC");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        ApartmentDTO apartmentResponse = response.getBody().get(0);
        assertEquals(2, apartmentResponse.numberOfBedrooms());
        assertEquals(1, apartmentResponse.numberOfBathrooms());
        assertEquals(60.40, apartmentResponse.area());
        assertEquals(false, apartmentResponse.hasParking());
        assertEquals("This is the first apartment", apartmentResponse.description());
    }

    @Test
    @Order(1)
    void getAllApartmentsWithANullFilter() {
        ResponseEntity<List<ApartmentDTO>> response = apartmentsController.getAllApartments(null, "ASC");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(3, response.getBody().size());
    }

    @Test
    @Order(2)
    void getApartmentById() {
        ResponseEntity<ApartmentDTO> response = apartmentsController.getApartment("F1rsT-@p4rtm3nt");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(response.getBody().id(), "F1rsT-@p4rtm3nt");
        assertEquals(response.getBody().numberOfBedrooms(), 2);
        assertEquals(response.getBody().numberOfBathrooms(), 1);
        assertEquals(response.getBody().hasParking(), false);
        assertEquals(response.getBody().area(), 60.40);
        assertEquals(response.getBody().price(), 1580);
        assertEquals(response.getBody().description(), "This is the first apartment");
    }

    @Test
    @Order(2)
    void getApartmentNonExistingId() {
        ResponseEntity<ApartmentDTO> response = apartmentsController.getApartment("1nv@l1d-iD");
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    @Order(3)
    void createApartmentProduceAnError() {
        ApartmentEntity apartment = new ApartmentEntity()
                .setNumberOfBedrooms(-4)
                .setNumberOfBathrooms(2)
                .setArea(52.1)
                .setPrice(2800.0F)
                .setHasParking(true)
                .setDescription("Quis non etiam nisl in aliquam potenti ac lorem, odio suspendisse netus auctor finibus pharetra mus placerat");
        assertThrows(RuntimeException.class, () -> apartmentsController.createApartment(apartment));
    }

    @Test
    @Order(5)
    void deleteApartment() {
        ResponseEntity<List<ApartmentDTO>> response = apartmentsController.getAllApartments(null, "ASC");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(3, response.getBody().size());
        apartmentsController.deleteApartment("Th1rd-@p4rtm3nt");
        response = apartmentsController.getAllApartments(null, "ASC");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
    }

    @Test
    @Order(6)
    void editApartmentWithValidValues() {
        ApartmentEntity apartment = new ApartmentEntity()
                .setNumberOfBedrooms(2)
                .setNumberOfBathrooms(2)
                .setArea(68.0)
                .setPrice(1234F)
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
    @Order(6)
    void editApartmentWithInvalidValues() {
        ApartmentEntity apartment = new ApartmentEntity()
                .setNumberOfBedrooms(2)
                .setNumberOfBathrooms(2)
                .setArea(-68.0)
                .setPrice(1234F)
                .setDescription("Ut litora tellus nisi a ridiculus orci praesent penatibus blandit cubilia arcu at ipsum");

        assertThrows(RuntimeException.class, () -> apartmentsController.editApartment("F1rsT-@p4rtm3nt", apartment.toDTO()));
    }

    @Test
    @Order(6)
    void shouldEditApartment() {
        ApartmentEntity apartment = new ApartmentEntity()
                .setNumberOfBedrooms(2)
                .setNumberOfBathrooms(2)
                .setArea(68.0)
                .setPrice(1234F)
                .setDescription("Ut litora tellus nisi a ridiculus orci praesent penatibus blandit cubilia arcu at ipsum");
        ResponseEntity<ApartmentDTO> response = apartmentsController.editApartment("F1rsT-@p4rtm3nt", apartment.toDTO());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        // Should not change
        assertEquals(false, response.getBody().hasParking());
        assertEquals(1234.0F, response.getBody().price());

        // Should have changed according to the values above
        assertEquals(2, response.getBody().numberOfBedrooms());
        assertEquals(2, response.getBody().numberOfBathrooms());
        assertEquals(68.0, response.getBody().area());
        assertEquals("Ut litora tellus nisi a ridiculus orci praesent penatibus blandit cubilia arcu at ipsum",
                response.getBody().description());
    }

    @Test
    @Order(6)
    void createApartment() {
        ApartmentEntity apartment = new ApartmentEntity()
                .setNumberOfBedrooms(3)
                .setNumberOfBathrooms(2)
                .setArea(62.1)
                .setPrice(3000.0F)
                .setHasParking(true)
                .setDescription("Quis non etiam nisl in aliquam potenti ac lorem, odio suspendisse netus auctor finibus pharetra mus placerat");
        ResponseEntity<ApartmentDTO> response = apartmentsController.createApartment(apartment);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        assertEquals(3, response.getBody().numberOfBedrooms());
        assertEquals(2, response.getBody().numberOfBathrooms());
        assertEquals(62.1, response.getBody().area());
        assertEquals(3000.0F, response.getBody().price());
        assertEquals(true, response.getBody().hasParking());
        assertEquals("Quis non etiam nisl in aliquam potenti ac lorem, odio suspendisse netus auctor finibus pharetra mus placerat",
                response.getBody().description());
    }
}