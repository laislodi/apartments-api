package com.rentals.apartment.controller;

import com.rentals.apartment.domain.ApartmentEntity;
import com.rentals.apartment.domain.ApartmentFilter;
import com.rentals.apartment.domain.ApartmentDTO;
import com.rentals.apartment.service.ApartmentService;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<ApartmentDTO>> getAllApartmentsWithSpecifications(
            ApartmentFilter filter,
            @RequestParam(required = false, defaultValue = "ASC") String order
    ) {
        // TODO: adjust ResponseEntity to return the right codes
        return ResponseEntity.ok(apartmentService.getAllApartmentsWithSpecifications(order, filter));
    }

    @GetMapping("/apartments-old")
    public ResponseEntity<List<ApartmentDTO>> getAllApartments(
            ApartmentFilter filter,
            @RequestParam(required = false, defaultValue = "ASC") String order
    ) {
        return ResponseEntity.ok(apartmentService.getAllApartmentsWithCustom(order, filter));
    }

    @GetMapping("/apartments/{id}")
    public ResponseEntity<ApartmentDTO> getApartment(@PathVariable String id) throws Exception {
        return ResponseEntity.ok(apartmentService.getApartmentById(id));
    }

    @PostMapping("/apartments/new")
    public ResponseEntity<ApartmentDTO> createApartment(@RequestBody ApartmentEntity newApartment) {
        return ResponseEntity.ok(apartmentService.createApartment(newApartment));
    }

    @PutMapping("/apartments/{id}")
    public ResponseEntity<ApartmentEntity> editApartment(@PathVariable String id, @RequestBody ApartmentEntity apartment) {
        return ResponseEntity.ok(apartmentService.editApartment(id, apartment));
    }
}
