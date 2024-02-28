package com.rentals.apartment.service.filter;

import com.rentals.apartment.domain.ApartmentBean;

import java.util.List;

public interface StringFilterHandler extends FilterHandler {

    List<ApartmentBean> applyStringFilter(List<ApartmentBean> apartmentBeans, String filter);
}
