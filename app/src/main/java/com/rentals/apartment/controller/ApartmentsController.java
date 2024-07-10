package com.rentals.apartment.controller;

import com.rentals.apartment.domain.ApartmentEntity;
import com.rentals.apartment.domain.ApartmentFilter;
import com.rentals.apartment.domain.ApartmentDTO;
import com.rentals.apartment.service.ApartmentService;
import org.hibernate.ObjectNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class ApartmentsController {

    private final ApartmentService apartmentService;

    public ApartmentsController(ApartmentService apartmentService) {
        this.apartmentService = apartmentService;
    }

    @GetMapping("/apartments")
    public ResponseEntity<List<ApartmentDTO>> getAllApartmentsWithSpecifications(
            ApartmentFilter filter,
            @RequestParam(required = false, defaultValue = "ASC") String order
    ) {
        List<ApartmentDTO> list = apartmentService.getAllApartmentsWithSpecifications(order, filter);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/apartments-old")
    public ResponseEntity<List<ApartmentDTO>> getAllApartments(
            ApartmentFilter filter,
            @RequestParam(required = false, defaultValue = "ASC") String order
    ) {
        return ResponseEntity.ok(apartmentService.getAllApartmentsWithCustom(order, filter));
    }

    @GetMapping("/apartments/{id}")
    public ResponseEntity<ApartmentDTO> getApartment(@PathVariable String id) {
        ApartmentDTO apartment;
        try {
            apartment = apartmentService.getApartmentById(id);
        } catch (ObjectNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok(apartment);
    }

    @PostMapping("/apartments/add")
    public ResponseEntity<ApartmentDTO> createApartment(
            @RequestBody ApartmentEntity newApartment
    ) {
        return ResponseEntity.ok(apartmentService.createApartment(newApartment));
    }

    @PutMapping("/apartments/{id}")
    public ResponseEntity<ApartmentDTO> editApartment(
            @PathVariable String id,
            @RequestBody ApartmentDTO apartment) {
        return ResponseEntity.ok(apartmentService.editApartment(id, apartment.toEntity()));
    }

    @DeleteMapping("/apartment/{id}")
    public ResponseEntity<String> deleteApartment(@PathVariable String id) {
        apartmentService.deleteApartment(id);
        return ResponseEntity.ok(id);
    }

}
