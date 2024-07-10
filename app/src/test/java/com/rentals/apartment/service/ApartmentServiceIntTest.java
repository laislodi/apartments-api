package com.rentals.apartment.service;

import com.rentals.apartment.domain.ApartmentDTO;
import com.rentals.apartment.domain.ApartmentEntity;
import com.rentals.apartment.domain.ApartmentFilter;
import com.rentals.apartment.repositories.ApartmentRepository;
import com.rentals.apartment.repositories.ApartmentRepositoryCustom;
import com.rentals.apartment.repositories.specifications.ApartmentSpecifications;
import org.hibernate.ObjectNotFoundException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.JdbcConnectionDetails;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;


@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("ApartmentServiceIntTest: tests with apartment service")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ApartmentServiceIntTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16.1");

    @Autowired
    ApartmentRepository apartmentRepository;
    @Autowired
    JdbcConnectionDetails jdbcConnectionDetails;
    @Autowired
    ApartmentRepositoryCustom apartmentRepositoryCustom;
    @Autowired
    ApartmentSpecifications apartmentSpecifications;
    @Autowired
    ApartmentService apartmentService;

    @BeforeEach
    void setup() {
        postgres.start();
        apartmentService = new ApartmentService(apartmentRepositoryCustom, apartmentRepository, apartmentSpecifications);
    }

    @AfterAll
    static void finish() {
        postgres.close();
    }

    @Test
    void connectionEstablished() {
        Assertions.assertTrue(postgres.isCreated());
        Assertions.assertTrue(postgres.isRunning());
    }

    @Test
    @DisplayName("Should retrieve an apartment by ID")
    void shouldRetrieveAnApartmentById() {
        ApartmentDTO apartmentDTO = apartmentService.getApartmentById("F1rsT-@p4rtm3nt");

        Assertions.assertEquals("F1rsT-@p4rtm3nt", apartmentDTO.id());
        Assertions.assertEquals(2, apartmentDTO.numberOfBedrooms());
        Assertions.assertEquals(1, apartmentDTO.numberOfBathrooms());
        Assertions.assertEquals(false, apartmentDTO.hasParking());
        Assertions.assertEquals(1580F, apartmentDTO.price());
        Assertions.assertEquals(60.4, apartmentDTO.area());
        Assertions.assertEquals("This is the first apartment",
                apartmentDTO.description());
    }

    @Test
    @DisplayName("Should Throw an ObjectNotFoundException when trying to retrieve an apartment that does not exist")
    void shouldThrowErrorApartmentNotFoundRetrievingAnApartmentById() {
        Assertions.assertThrows(ObjectNotFoundException.class,
                () -> apartmentService.getApartmentById("N0t-a-v4l1d-ID"));
    }

    @Test
    @DisplayName("Should create a new apartment")
    void shouldCreateAnApartment() {
        ApartmentEntity apartment = new ApartmentEntity().setNumberOfBedrooms(2).setNumberOfBathrooms(2)
                .setArea(51.2).setHasParking(false).setPrice(2300F)
                .setDescription("Aenean non elementum conubia commodo est, elit arcu habitant natoque efficitur auctor, himenaeos dolor aliquam at.");
        ApartmentDTO dto = apartmentService.createApartment(apartment);
        Assertions.assertEquals(2, dto.numberOfBedrooms());
        Assertions.assertEquals(2, dto.numberOfBathrooms());
        Assertions.assertEquals(51.2, dto.area());
        Assertions.assertEquals(2300F, dto.price());
        Assertions.assertEquals(false, dto.hasParking());
        Assertions.assertEquals("Aenean non elementum conubia commodo est, elit arcu habitant natoque efficitur auctor, himenaeos dolor aliquam at.", dto.description());
    }

    @Test
    @DisplayName("should edit just the number of bedrooms of an apartment")
    void shouldEditJustTheNumberOfBedroomsOfAnApartment() {
        ApartmentEntity apartment = new ApartmentEntity().setNumberOfBedrooms(2);
        ApartmentDTO editedApartment = apartmentService.editApartment("S3c0nd-@p4rtm3nt", apartment);

        Assertions.assertNotNull(editedApartment);
        Assertions.assertEquals("S3c0nd-@p4rtm3nt", editedApartment.id());
        Assertions.assertEquals(2, editedApartment.numberOfBedrooms());
        Assertions.assertEquals(2, editedApartment.numberOfBathrooms());
        Assertions.assertEquals(true, editedApartment.hasParking());
        Assertions.assertEquals(2300.00F, editedApartment.price());
        Assertions.assertEquals(72.8, editedApartment.area());
        Assertions.assertEquals("This is the second apartment",
                editedApartment.description());
    }

    @Test
    @DisplayName("should edit just the number of bathrooms of an apartment")
    void shouldEditAnApartment() {
        ApartmentEntity apartment = new ApartmentEntity()
                .setNumberOfBathrooms(2)
                .setHasParking(true)
                .setPrice(2500F)
                .setArea(65.8)
                .setDescription("This is the first apartment, but renovated");
        ApartmentDTO editedApartment = apartmentService.editApartment("F1rsT-@p4rtm3nt", apartment);

        Assertions.assertEquals("F1rsT-@p4rtm3nt", editedApartment.id());
        Assertions.assertEquals(2, editedApartment.numberOfBedrooms());
        Assertions.assertEquals(2, editedApartment.numberOfBathrooms());
        Assertions.assertEquals(true, editedApartment.hasParking());
        Assertions.assertEquals(2500F, editedApartment.price());
        Assertions.assertEquals(65.8, editedApartment.area());
        Assertions.assertEquals("This is the first apartment, but renovated",
                editedApartment.description());
    }

    @Test
    @DisplayName("Should throw an ObjectNotFoundException when try to edit a non existing apartment")
    void shouldThrowObjectNotFoundExceptionWhenEditingANonExistingApartment() {
        ApartmentEntity apartment = new ApartmentEntity()
                .setNumberOfBathrooms(2)
                .setHasParking(true);
        Assertions.assertThrows(ObjectNotFoundException.class,
                () -> apartmentService.editApartment("1nv@lid-Id", apartment));
    }

    @Test
    @DisplayName("Should filter apartments by number of Bedrooms")
    void shouldFilterApartmentsByNumberOfBedrooms() {
        ApartmentFilter filter = new ApartmentFilter("2", null, null, null, null, null, null, null);

        List<ApartmentDTO> filteredApartments = apartmentService.getAllApartmentsWithSpecifications("ASC", filter);

        Assertions.assertEquals(1, filteredApartments.size());

        Assertions.assertEquals("F1rsT-@p4rtm3nt", filteredApartments.get(0).id());
        Assertions.assertEquals(2, filteredApartments.get(0).numberOfBedrooms());
        Assertions.assertEquals(1, filteredApartments.get(0).numberOfBathrooms());
        Assertions.assertEquals(false, filteredApartments.get(0).hasParking());
        Assertions.assertEquals(1580F, filteredApartments.get(0).price());
        Assertions.assertEquals(60.4, filteredApartments.get(0).area());
        Assertions.assertEquals("This is the first apartment",
                filteredApartments.get(0).description());

    }

    @Test
    @DisplayName("Should filter apartments by area")
    void shouldFilterApartmentsByArea() {
        ApartmentFilter filter = new ApartmentFilter(null, null, 60f, 70f, null, null, null, null);

        List<ApartmentDTO> filteredApartments = apartmentService.getAllApartmentsWithSpecifications("ASC", filter);

        Assertions.assertEquals(1, filteredApartments.size());

        Assertions.assertEquals("F1rsT-@p4rtm3nt", filteredApartments.get(0).id());
        Assertions.assertEquals(2, filteredApartments.get(0).numberOfBedrooms());
        Assertions.assertEquals(1, filteredApartments.get(0).numberOfBathrooms());
        Assertions.assertEquals(false, filteredApartments.get(0).hasParking());
        Assertions.assertEquals(1580F, filteredApartments.get(0).price());
        Assertions.assertEquals(60.4, filteredApartments.get(0).area());
        Assertions.assertEquals("This is the first apartment",
                filteredApartments.get(0).description());

    }
}
