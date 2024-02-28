package com.rentals.apartment.service.filter;

import com.rentals.apartment.domain.ApartmentBean;

import java.util.List;
import java.util.Objects;

public class PriceFilterHandler implements MinimumFilterHandler {
    public FilterHandler next;

    public PriceFilterHandler(FilterHandler next) {
        this.next = next;
    }

    public PriceFilterHandler() {
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
        return Objects.nonNull(filter) && Objects.nonNull(filter.getPrice());
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
                    apartmentBean -> apartmentBean.getPrice() >= filter.getPrice().getMinimum() &&
                            apartmentBean.getPrice() <= filter.getPrice().getMaximum()
            ).toList();
        }
        return apartmentBeans;
    }
}
