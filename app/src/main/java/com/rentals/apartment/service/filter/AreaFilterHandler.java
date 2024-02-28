package com.rentals.apartment.service.filter;

import com.rentals.apartment.domain.ApartmentBean;

import java.util.List;
import java.util.Objects;

public class AreaFilterHandler implements RangeFilterHandler {
    public FilterHandler next;

    public AreaFilterHandler() {
        super();
    }

    public AreaFilterHandler(FilterHandler next) {
        this.next = next;
    }

    @Override
    public FilterHandler setNextHandler(FilterHandler next) {
        this.next = next;
        return next;
    }

    @Override
    public FilterHandler getNext() {
        return next;
    }

    @Override
    public boolean shouldFilter(ApartmentFilter filter) {
        return Objects.nonNull(filter) && Objects.nonNull(filter.getArea());
    }

    @Override
    public List<ApartmentBean> applyFilter(List<ApartmentBean> apartmentBeans, ApartmentFilter filter) {
        List<ApartmentBean> list = applyRangeFilter(apartmentBeans, filter);
        if (Objects.isNull(next)) {
            return list;
        }
        return next.applyFilter(list, filter);
    }

    @Override
    public List<ApartmentBean> applyRangeFilter(List<ApartmentBean> apartmentBeans, ApartmentFilter filter) {
        if (shouldFilter(filter)) {
            return apartmentBeans.stream().filter(
                    apartmentBean -> apartmentBean.getArea() >= filter.getArea().getMinimum() &&
                            apartmentBean.getArea() <= filter.getArea().getMaximum()
            ).toList();
        }
        return apartmentBeans;
    }
}
