package com.rentals.apartment.controller;

import com.rentals.apartment.domain.ApartmentBean;
import com.rentals.apartment.domain.ApartmentRecord;
import com.rentals.apartment.service.ApartmentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping()
public class ApartmentsApi {

    private final ApartmentService apartmentService;

    public ApartmentsApi(ApartmentService apartmentService) {
        this.apartmentService = apartmentService;
    }

    @GetMapping("/apartments")
    public List<ApartmentRecord> getAllApartments(
            @RequestParam(required = false) String bedrooms,
            @RequestParam(required = false) String bathrooms,
            @RequestParam(required = false) String minArea,
            @RequestParam(required = false) String maxArea,
            @RequestParam(required = false) boolean hasParking,
            @RequestParam(required = false) String minPrice,
            @RequestParam(required = false) String maxPrice,
            @RequestParam(required = false) String description,
            @RequestParam(required = false, defaultValue = "ASC") String order
            ) {
        return apartmentService.getAllApartments(order, bedrooms, bathrooms, minArea, maxArea, hasParking, minPrice, maxPrice, description);
    }

    @GetMapping("/apartments/{id}")
    public ApartmentRecord getApartment(@PathVariable String id) throws Exception {
        return apartmentService.getApartmentById(id);
    }

    @PostMapping("/apartments/new")
    public ApartmentRecord createApartment(@RequestBody ApartmentBean newApartment) {
        return apartmentService.createApartment(newApartment);
    }

    @PutMapping("/apartments/{id}")
    public ApartmentBean editApartment(@PathVariable String id, @RequestBody ApartmentBean apartment) {
        return apartmentService.editApartment(id, apartment);
    }
}
