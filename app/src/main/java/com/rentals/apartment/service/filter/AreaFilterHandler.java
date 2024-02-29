package com.rentals.apartment.service.filter;

import com.rentals.apartment.domain.ApartmentBean;

import java.util.List;
import java.util.Objects;

public class AreaFilterHandler implements RangeFilterHandler {
    public AreaFilterHandler() {
        super();
    }

    @Override
    public boolean shouldFilter(ApartmentFilterParam filter) {
        return Objects.nonNull(filter) && Objects.nonNull(filter.getArea());
    }

    @Override
    public List<ApartmentBean> applyFilter(List<ApartmentBean> apartmentBeans, ApartmentFilterParam filter) {
        return applyRangeFilter(apartmentBeans, filter);
    }

    @Override
    public List<ApartmentBean> applyRangeFilter(List<ApartmentBean> apartmentBeans, ApartmentFilterParam filter) {
        if (shouldFilter(filter)) {
            return apartmentBeans.stream().filter(
                    apartmentBean -> apartmentBean.getArea() >= filter.getArea().getMinimum() &&
                            apartmentBean.getArea() <= filter.getArea().getMaximum()
            ).toList();
        }
        return apartmentBeans;
    }
}
