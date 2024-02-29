package com.rentals.apartment.service.filter;

import com.rentals.apartment.domain.ApartmentBean;

import java.util.List;

public interface RangeFilterHandler extends FilterHandler {

    List<ApartmentBean> applyRangeFilter(List<ApartmentBean> apartmentBeans, ApartmentFilterParam filter);
}
