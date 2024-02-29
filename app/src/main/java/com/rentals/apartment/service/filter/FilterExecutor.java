package com.rentals.apartment.service.filter;

import com.rentals.apartment.domain.ApartmentBean;

import java.util.ArrayList;
import java.util.List;

public class FilterExecutor {

    public List<ApartmentBean> filter(List<ApartmentBean> apartmentBeans, List<FilterHandler> filterHandlers, ApartmentFilterParam filter) {
        List<ApartmentBean> reduced = apartmentBeans;
        for (FilterHandler filterHandler : filterHandlers) {
            if (filterHandler.shouldFilter(filter)) {
                reduced = filterHandler.applyFilter(reduced, filter);
            }
        }
        return reduced;
    }
}
