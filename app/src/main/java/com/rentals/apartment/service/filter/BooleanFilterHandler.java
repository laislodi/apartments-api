package com.rentals.apartment.service.filter;

import com.rentals.apartment.domain.ApartmentBean;

import java.util.List;

public interface BooleanFilterHandler extends FilterHandler {

    List<ApartmentBean> applyBooleanFilter(List<ApartmentBean> apartmentBeans, Boolean filter);
}
