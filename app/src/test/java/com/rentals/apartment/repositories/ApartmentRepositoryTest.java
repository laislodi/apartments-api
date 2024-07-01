package com.rentals.apartment.repositories;

import com.rentals.apartment.domain.ApartmentDTO;
import com.rentals.apartment.domain.ApartmentEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.JdbcConnectionDetails;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Testcontainers
// By default, tests annotated with @DataJdbcTest are transactional and roll back at the end of each test.
// They also use an embedded in-memory database (replacing any explicit or usually auto-configured DataSource).
// The @AutoConfigureTestDatabase annotation can be used to override these settings.
//@DataJdbcTest
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DisplayName("ApartmentRepositoryTest: tests with apartment repository auto configuration")
public class ApartmentRepositoryTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16.1");

    @Autowired
    ApartmentRepository apartmentRepository;
    @Autowired
    JdbcConnectionDetails jdbcConnectionDetails;

    @BeforeEach
    void setup() {
        List<ApartmentEntity> apartmentList = List.of(
                new ApartmentEntity().setId("N3w-@P4rTm3nt").setNumberOfBedrooms(3)
                        .setNumberOfBathrooms(2).setPrice(3000F).setHasParking(true).setArea(67.2)
                        .setDescription("Lorem ipsum dolor sit amet consectetur adipiscing elit lobortis, proin natoque id porttitor mus euismod auctor lectus velit"),
                new ApartmentEntity().setId("0th3r-@P4rTm3nt").setNumberOfBedrooms(1)
                        .setNumberOfBathrooms(1).setPrice(1500F).setHasParking(false).setArea(28.9)
                        .setDescription("Netus habitasse pretium mus at primis finibus sit ut pharetra, venenatis praesent aliquam est donec ipsum consectetur imperdiet maecenas viverra"),
                new ApartmentEntity().setId("4N0th3r-@P4rTm3nt").setNumberOfBedrooms(2)
                        .setNumberOfBathrooms(1).setPrice(2000F).setHasParking(false).setArea(34.1)
                        .setDescription("Ut litora tellus nisi a ridiculus orci praesent penatibus blandit cubilia arcu at ipsum"));
        apartmentRepository.saveAll(apartmentList);

    }

    @Test
    void connectionEstablished() {
        Assertions.assertTrue(postgres.isCreated());
        Assertions.assertTrue(postgres.isRunning());
    }

    @Test
    void shouldFindAllApartments() {
        Iterable<ApartmentEntity> findAllApartments = apartmentRepository.findAll();
        List<ApartmentEntity> allApartments = new ArrayList<>();
        for (ApartmentEntity apartment :
                findAllApartments) {
            allApartments.add(apartment);
        }

        Assertions.assertEquals(6, allApartments.size());
        // The three first apartments are created with resources/db/migration/V9999.1.__test-data.sql
        Assertions.assertEquals("F1rsT-@p4rtm3nt", allApartments.get(0).getId());
        Assertions.assertEquals("S3c0nd-@p4rtm3nt", allApartments.get(1).getId());
        Assertions.assertEquals("Th1rd-@p4rtm3nt", allApartments.get(2).getId());
        // The next apartments are created with the @BeforeEach function above
        Assertions.assertEquals("N3w-@P4rTm3nt", allApartments.get(3).getId());
        Assertions.assertEquals("0th3r-@P4rTm3nt", allApartments.get(4).getId());
        Assertions.assertEquals("4N0th3r-@P4rTm3nt", allApartments.get(5).getId());
    }

    @Test
    void shouldEditApartment() {

    }

    @Test
    void shouldRetrieveAnApartmentById() {
        Optional<ApartmentEntity> optional = apartmentRepository.findById("N3w-@P4rTm3nt");

        ApartmentEntity apartment = optional.orElse(null);
        Assertions.assertNotNull(apartment);
        Assertions.assertEquals("N3w-@P4rTm3nt", apartment.getId());
        Assertions.assertEquals(3, apartment.getNumberOfBedrooms());
        Assertions.assertEquals(2, apartment.getNumberOfBathrooms());
        Assertions.assertEquals(true, apartment.getHasParking());
        Assertions.assertEquals(3000F, apartment.getPrice());
        Assertions.assertEquals(67.2, apartment.getArea());
        Assertions.assertEquals("Lorem ipsum dolor sit amet consectetur adipiscing elit lobortis, proin natoque id porttitor mus euismod auctor lectus velit",
                apartment.getDescription());
    }

}
