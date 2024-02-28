package com.rentals.apartment.service.filter;

import com.rentals.apartment.domain.ApartmentBean;

import java.util.List;
import java.util.Objects;

public class DescriptionFilterHandler implements StringFilterHandler {

    public FilterHandler next;

    public DescriptionFilterHandler(FilterHandler next) {
        this.next = next;
    }

    public DescriptionFilterHandler() {
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
        return Objects.nonNull(filter) && Objects.nonNull(filter.getDescription());
    }

    @Override
    public List<ApartmentBean> applyFilter(List<ApartmentBean> apartmentBeans, ApartmentFilter filter) {
        List<ApartmentBean> list = applyStringFilter(apartmentBeans, filter.getDescription());
        if (Objects.isNull(next)) {
            return list;
        }
        return next.applyFilter(list, filter);
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
