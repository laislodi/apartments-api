package com.rentals.apartment.service.filter;

import com.rentals.apartment.domain.ApartmentBean;

import java.util.List;
import java.util.Objects;

public class DescriptionFilterHandler implements StringFilterHandler {

    public DescriptionFilterHandler() {
        super();
    }

    @Override
    public boolean shouldFilter(ApartmentFilterParam filter) {
        return Objects.nonNull(filter) && Objects.nonNull(filter.getDescription());
    }

    @Override
    public List<ApartmentBean> applyFilter(List<ApartmentBean> apartmentBeans, ApartmentFilterParam filter) {
        return applyStringFilter(apartmentBeans, filter.getDescription());
    }

    @Override
    public List<ApartmentBean> applyStringFilter(List<ApartmentBean> apartmentBeans, String filter) {
        if (Objects.nonNull(filter)){
            return apartmentBeans.stream().filter(
                    apartmentBean -> apartmentBean.getDescription().toLowerCase().contains(filter.toLowerCase())
            ).toList();
        }
        return apartmentBeans;
    }
}
