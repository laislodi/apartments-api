package com.rentals.apartment.service.filter;

import com.rentals.apartment.domain.ApartmentBean;

import java.util.List;
import java.util.Objects;

public class PriceFilterHandler implements MinimumFilterHandler {
    public PriceFilterHandler() {
        super();
    }

    @Override
    public boolean shouldFilter(ApartmentFilterParam filter) {
        return Objects.nonNull(filter) && Objects.nonNull(filter.getPrice());
    }

    @Override
    public List<ApartmentBean> applyFilter(List<ApartmentBean> apartmentBeans, ApartmentFilterParam filter) {
        return applyMinimumFilter(apartmentBeans, filter);
    }

    @Override
    public List<ApartmentBean> applyMinimumFilter(List<ApartmentBean> apartmentBeans, ApartmentFilterParam filter) {
        if (shouldFilter(filter)) {
            return apartmentBeans.stream().filter(
                    apartmentBean -> apartmentBean.getPrice() >= filter.getPrice().getMinimum() &&
                            apartmentBean.getPrice() <= filter.getPrice().getMaximum()
            ).toList();
        }
        return apartmentBeans;
    }
}
