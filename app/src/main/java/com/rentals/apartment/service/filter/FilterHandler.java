package com.rentals.apartment.service.filter;

import com.rentals.apartment.domain.ApartmentBean;

import java.util.List;

public interface FilterHandler {

    boolean shouldFilter(ApartmentFilterParam filter);

     List<ApartmentBean> applyFilter(List<ApartmentBean> apartmentBeans, ApartmentFilterParam filter);
}
