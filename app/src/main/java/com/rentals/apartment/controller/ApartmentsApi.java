package com.rentals.apartment.controller;

import com.rentals.apartment.domain.ApartmentBean;
import com.rentals.apartment.domain.ApartmentRecord;
import com.rentals.apartment.service.ApartmentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

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
            @RequestParam Map<String, String> filter,
            @RequestParam(required = false, defaultValue = "ASC") String order
    ) {
        return apartmentService.getAllApartments(order, filter);
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
