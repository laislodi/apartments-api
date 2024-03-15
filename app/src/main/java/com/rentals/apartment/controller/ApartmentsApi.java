package com.rentals.apartment.controller;

import com.rentals.apartment.domain.ApartmentEntity;
import com.rentals.apartment.domain.ApartmentFilter;
import com.rentals.apartment.domain.ApartmentDTO;
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
    public List<ApartmentDTO> getAllApartments(
            ApartmentFilter filter,
            @RequestParam(required = false, defaultValue = "ASC") String order
    ) {
        return apartmentService.getAllApartments(order, filter);
    }

    @GetMapping("/apartments/{id}")
    public ApartmentDTO getApartment(@PathVariable String id) throws Exception {
        return apartmentService.getApartmentById(id);
    }

    @PostMapping("/apartments/new")
    public ApartmentDTO createApartment(@RequestBody ApartmentEntity newApartment) {
        return apartmentService.createApartment(newApartment);
    }

    @PutMapping("/apartments/{id}")
    public ApartmentEntity editApartment(@PathVariable String id, @RequestBody ApartmentEntity apartment) {
        return apartmentService.editApartment(id, apartment);
    }
}
