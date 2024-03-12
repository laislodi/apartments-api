package com.rentals.apartment.service;

import com.google.common.collect.Lists;
import com.rentals.apartment.domain.ApartmentBean;
import com.rentals.apartment.domain.ApartmentRecord;
import com.rentals.apartment.repositories.ApartmentRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ApartmentService {

    private final ApartmentRepository apartmentRepository;

    public ApartmentService(ApartmentRepository apartmentRepository) {
        this.apartmentRepository = apartmentRepository;
    }

    private boolean minimumFilter(int apartmentMinimum, String minimumStr) {
        if (Objects.isNull(minimumStr)) {
            return apartmentMinimum >= 0;
        }
        boolean greaterThan = minimumStr.contains("+");
        int filterMinimum = Integer.parseInt(minimumStr.replaceAll("\\+", ""));
        return greaterThan ? apartmentMinimum >= filterMinimum : apartmentMinimum == filterMinimum;
    }

    private boolean rangeFilter(double apartmentMin, String minStr, String maxStr) {
        boolean isBiggerThanMinimum = !Objects.nonNull(minStr) || apartmentMin >= Double.parseDouble(minStr);
        boolean isSmallerThanMaximum = !Objects.nonNull(maxStr) || apartmentMin <= Double.parseDouble(maxStr);
        return isBiggerThanMinimum && isSmallerThanMaximum;
    }

    private boolean booleanFilter(String bool) {
        return bool.equals("true");
    }

    public List<ApartmentRecord> getAllApartments(String orderBy, Map<String, String> filter) {
        List<ApartmentBean> allBeans = Lists.newArrayList(apartmentRepository.findAll());
        List<ApartmentBean> filteredApartments = allBeans.stream()
                .filter(ap -> ap.getDescription().toLowerCase().contains(filter.get("description").toLowerCase()))
                .filter(ap -> minimumFilter(ap.getNumberOfBedrooms(), filter.get("bedrooms")))
                .filter(ap -> minimumFilter(ap.getNumberOfBathrooms(), filter.get("bathrooms")))
                .filter(ap -> rangeFilter(ap.getArea(), filter.get("minArea"), filter.get("maxArea")))
                .filter(ap -> booleanFilter(filter.get("hasParking")) ? ap.getHasParking() : true)
                .filter(ap -> rangeFilter(ap.getPrice(), filter.get("minPrice"), filter.get("maxPrice")))
                .toList();
        List<ApartmentRecord> allRecords = new ArrayList<>();
        for (ApartmentBean bean:
                filteredApartments) {
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
