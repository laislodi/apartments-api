package com.rentals.apartment.service.filter;

import com.rentals.apartment.domain.ApartmentBean;

import java.util.List;

public class FilterExecutor {

    public List<ApartmentBean> filter(List<ApartmentBean> apartmentBeans, ApartmentFilter filter) {
        FilterHandler handler = new PriceFilterHandler();
        handler.setNextHandler(new AreaFilterHandler())
                .setNextHandler(new HasParkingHandler())
                .setNextHandler(new BathroomFilterHandler())
                .setNextHandler(new BedroomFilterHandler())
                .setNextHandler(new DescriptionFilterHandler());
        return handler.applyFilter(apartmentBeans, filter);
    }
}
