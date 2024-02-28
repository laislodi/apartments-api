package com.rentals.apartment.service.filter;

import com.rentals.apartment.domain.ApartmentBean;

import java.util.List;
import java.util.Objects;

public class BedroomFilterHandler implements MinimumFilterHandler {
    private FilterHandler next;

    public BedroomFilterHandler(FilterHandler next) {
        this.next = next;
    }

    public BedroomFilterHandler() {
        super();
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
        return Objects.nonNull(filter) && Objects.nonNull(filter.getBedrooms());
    }

    @Override
    public List<ApartmentBean> applyFilter(List<ApartmentBean> apartmentBeans, ApartmentFilter filter) {
        List<ApartmentBean> list = applyMinimumFilter(apartmentBeans, filter);
        if (Objects.isNull(next)) {
            return list;
        }
        return next.applyFilter(list, filter);
    }

    @Override
    public List<ApartmentBean> applyMinimumFilter(List<ApartmentBean> apartmentBeans, ApartmentFilter filter) {
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
