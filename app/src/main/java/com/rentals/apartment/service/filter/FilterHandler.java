package com.rentals.apartment.service.filter;

import com.rentals.apartment.domain.ApartmentBean;

import java.util.List;

public interface FilterHandler {
    FilterHandler setNextHandler(FilterHandler next);

    FilterHandler getNext();

    boolean shouldFilter(ApartmentFilter filter);

     List<ApartmentBean> applyFilter(List<ApartmentBean> apartmentBeans, ApartmentFilter filter);
}
