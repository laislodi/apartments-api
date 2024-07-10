package com.rentals.apartment.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ApartmentEntityTest {

    @Test
    void toRecord() {
        ApartmentDTO expectedDto = new ApartmentDTO("123-456-789", 3, 2, 50.5, false, 1000f, "qwert asdfg zxcvb");
        ApartmentEntity entity = new ApartmentEntity();
        entity.setId("123-456-789");
        entity.setNumberOfBedrooms(3);
        entity.setNumberOfBathrooms(2);
        entity.setArea(50.5);
        entity.setHasParking(false);
        entity.setPrice(1000f);
        entity.setDescription("qwert asdfg zxcvb");
        ApartmentDTO entityRecord = entity.toDTO();

        assertEquals(expectedDto, entityRecord);
    }
}