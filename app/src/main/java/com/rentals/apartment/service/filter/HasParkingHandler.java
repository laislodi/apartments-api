package com.rentals.apartment.service.filter;

import com.rentals.apartment.domain.ApartmentBean;

import java.util.List;
import java.util.Objects;

public class HasParkingHandler implements BooleanFilterHandler {
    public FilterHandler next;

    public HasParkingHandler(FilterHandler next) {
        this.next = next;
    }

    public HasParkingHandler() {
        super();
    }

    @Override
    public FilterHandler setNextHandler(FilterHandler next) {
        this.next = next;
        return next;
    }

    public FilterHandler getNext() {
        return next;
    }

    @Override
    public boolean shouldFilter(ApartmentFilter filter) {
        return Objects.nonNull(filter) && Objects.nonNull(filter.getHasParking());
    }

    @Override
    public List<ApartmentBean> applyFilter(List<ApartmentBean> apartmentBeans, ApartmentFilter filter) {
        List<ApartmentBean> list = applyBooleanFilter(apartmentBeans, filter.getHasParking());
        if (Objects.isNull(next)) {
            return list;
        }
        return next.applyFilter(list, filter);
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
