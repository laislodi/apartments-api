package com.rentals.apartment.service.filter;

import com.rentals.apartment.domain.ApartmentBean;

import java.util.List;
import java.util.Objects;


public interface MinimumFilterHandler extends FilterHandler {

    List<ApartmentBean> applyMinimumFilter(List<ApartmentBean> apartmentBeans, ApartmentFilter filter);
}
