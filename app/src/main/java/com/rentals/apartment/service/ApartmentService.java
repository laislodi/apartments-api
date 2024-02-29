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
    private MinimumFilter setMinimumFilter(String minimum) {
        if (minimum == null) {
            return new MinimumFilter(0, true);
        }
        boolean greaterThan = false;
        int min = 0;
        if (minimum.contains("+")) {
            greaterThan = true;
        }
        minimum = minimum.replaceAll("\\+", "");
        min = Integer.parseInt(minimum);
        return new MinimumFilter(min, greaterThan);
    }

    private RangeFilter setRangeFilter(String minimum, String maximum) {
        if (Objects.isNull(minimum)) {
            minimum = "0";
        }
        if (Objects.isNull(maximum)) {
            maximum = "999999";
        }
        Double min = Double.valueOf(minimum);
        Double max = Double.valueOf(maximum);
        return new RangeFilter(min, max);
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

    public List<ApartmentRecord> getAllApartments(String orderBy, String bedroom, String bathroom, String minArea, String maxArea, boolean hasParking, String minPrice, String maxPrice, String description) {
        List<ApartmentBean> allBeans = Lists.newArrayList(apartmentRepository.findAll());
        List<ApartmentBean> filteredApartments = allBeans.stream()
                .filter(ap -> minimumFilter(ap.getNumberOfBedrooms(), bedroom))
                .filter(ap -> minimumFilter(ap.getNumberOfBathrooms(), bathroom))
                .filter(ap -> rangeFilter(ap.getArea(), minArea, maxArea))
                .filter(ap -> hasParking ? ap.getHasParking() : true)
                .filter(ap -> rangeFilter(ap.getPrice(), minPrice, maxPrice))
                .toList();
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
