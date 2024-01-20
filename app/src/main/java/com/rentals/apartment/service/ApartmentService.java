package com.rentals.apartment.service;

import com.google.common.collect.Lists;
import com.rentals.apartment.domain.ApartmentBean;
import com.rentals.apartment.domain.ApartmentRecord;
import com.rentals.apartment.repositories.ApartmentRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ApartmentService {

    private final ApartmentRepository apartmentRepository;

    public ApartmentService(ApartmentRepository apartmentRepository) {
        this.apartmentRepository = apartmentRepository;
    }

    public List<ApartmentRecord> getAllApartments() {
        List<ApartmentBean> allBeans = Lists.newArrayList(apartmentRepository.findAll());
        List<ApartmentRecord> allRecords = new ArrayList<>();
        for (ApartmentBean bean:
             allBeans) {
            allRecords.add(bean.toRecord());
        }
        return allRecords;
    }

    public ApartmentRecord getApartmentById(String id) throws Exception {
        Optional<ApartmentBean> apartment = apartmentRepository.findById(id);
        if (apartment.isEmpty()) {
            throw new Exception("Apartment not found: id: %s".formatted(id));
        }
        return apartment.get().toRecord();
    }

    public ApartmentRecord createApartment(ApartmentBean apartment) {
        UUID uuid = UUID.randomUUID();
        apartment.setId(uuid.toString());
        ApartmentBean newApartment = apartmentRepository.save(apartment);
        return newApartment.toRecord();
    }

    public ApartmentBean editApartment(String id, ApartmentBean apartmentBean) {
        ApartmentBean apartment = apartmentRepository.findById(id).get();
        ApartmentBean newApartment = new ApartmentBean();
        newApartment.setNumberOfBedrooms(apartment.getNumberOfBedrooms());
        newApartment.setNumberOfBathrooms(apartment.getNumberOfBathrooms());
        newApartment.setArea(apartment.getArea());
        newApartment.setHasParking(apartment.getHasParking());
        newApartment.setPrice(apartment.getPrice());
        newApartment.setDescription(apartment.getDescription());

        newApartment.setId(id);
        newApartment.setNumberOfBedrooms(apartmentBean.getNumberOfBedrooms());
        newApartment.setNumberOfBathrooms(apartmentBean.getNumberOfBathrooms());
        newApartment.setArea(apartmentBean.getArea());
        newApartment.setHasParking(apartmentBean.getHasParking());
        newApartment.setPrice(apartmentBean.getPrice());
        newApartment.setDescription(apartmentBean.getDescription());

       return apartmentRepository.save(newApartment);
    }

}
