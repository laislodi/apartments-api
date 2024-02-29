package com.rentals.apartment.service.filter;

import com.rentals.apartment.domain.ApartmentBean;

import java.util.List;
import java.util.Objects;

public class BedroomFilterHandler implements MinimumFilterHandler {
    public BedroomFilterHandler() {
        super();
    }

    @Override
    public boolean shouldFilter(ApartmentFilterParam filter) {
        return Objects.nonNull(filter) && Objects.nonNull(filter.getBedrooms());
    }

    @Override
    public List<ApartmentBean> applyFilter(List<ApartmentBean> apartmentBeans, ApartmentFilterParam filter) {
        return applyMinimumFilter(apartmentBeans, filter);
    }

    @Override
    public List<ApartmentBean> applyMinimumFilter(List<ApartmentBean> apartmentBeans, ApartmentFilterParam filter) {
        if (shouldFilter(filter)) {
            return apartmentBeans.stream().filter(
                    apartmentBean -> filter.getBedrooms().isGreaterThan() ?
                            apartmentBean.getNumberOfBedrooms() >= filter.getBedrooms().getMinimum() :
                            Objects.equals(apartmentBean.getNumberOfBedrooms(), filter.getBedrooms().getMinimum())
            ).toList();
        }
        return apartmentBeans;
    }
}
