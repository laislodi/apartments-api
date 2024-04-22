package com.rentals.apartment.controller;

import com.rentals.apartment.domain.ApartmentEntity;
import com.rentals.apartment.domain.ApartmentFilter;
import com.rentals.apartment.domain.ApartmentDTO;
import com.rentals.apartment.service.ApartmentService;
import org.hibernate.ObjectNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ApartmentsController implements ControllerConfig {

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
        if (list.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        // TODO: How to return Unauthorized?
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

    @PostMapping("/apartments/new")
    public ResponseEntity<ApartmentDTO> createApartment(
            @RequestBody ApartmentEntity newApartment
    ) {
        return ResponseEntity.ok(apartmentService.createApartment(newApartment));
    }

    @PutMapping("/apartments/{id}")
    public ResponseEntity<ApartmentEntity> editApartment(
            @PathVariable String id,
            @RequestBody ApartmentEntity apartment) {
        return ResponseEntity.ok(apartmentService.editApartment(id, apartment));
    }
}
