package com.rentals.apartment.service.filter;

import com.rentals.apartment.domain.ApartmentBean;

import java.util.List;
import java.util.Objects;

public class BathroomFilterHandler implements MinimumFilterHandler {
    public BathroomFilterHandler() {
        super();
    }

    @Override
    public boolean shouldFilter(ApartmentFilterParam filter) {
        return Objects.nonNull(filter) && Objects.nonNull(filter.getBathrooms());
    }

    @Override
    public List<ApartmentBean> applyFilter(List<ApartmentBean> apartmentBeans, ApartmentFilterParam filter) {
        return applyMinimumFilter(apartmentBeans, filter);
    }

    @Override
    public List<ApartmentBean> applyMinimumFilter(List<ApartmentBean> apartmentBeans, ApartmentFilterParam filter) {
        if (shouldFilter(filter)) {
            return apartmentBeans.stream().filter(
                    apartmentBean -> filter.getBathrooms().isGreaterThan() ?
                            apartmentBean.getNumberOfBathrooms() >= filter.getBathrooms().getMinimum() :
                            Objects.equals(apartmentBean.getNumberOfBathrooms(), filter.getBathrooms().getMinimum())
            ).toList();
        }
        return apartmentBeans;
    }
}
