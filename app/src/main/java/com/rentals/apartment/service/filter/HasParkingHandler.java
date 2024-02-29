package com.rentals.apartment.service.filter;

import com.rentals.apartment.domain.ApartmentBean;

import java.util.List;
import java.util.Objects;

public class HasParkingHandler implements BooleanFilterHandler {

    public HasParkingHandler() {
        super();
    }

    @Override
    public boolean shouldFilter(ApartmentFilterParam filter) {
        return Objects.nonNull(filter) && Objects.nonNull(filter.getHasParking());
    }

    @Override
    public List<ApartmentBean> applyFilter(List<ApartmentBean> apartmentBeans, ApartmentFilterParam filter) {
        return applyBooleanFilter(apartmentBeans, filter.getHasParking());
    }

    @Override
    public List<ApartmentBean> applyBooleanFilter(List<ApartmentBean> apartmentBeans, Boolean filter) {
        if (Objects.isNull(filter) || !filter) {
            return apartmentBeans;
        }
        return apartmentBeans.stream().filter(
                ApartmentBean::getHasParking
        ).toList();
    }
}
