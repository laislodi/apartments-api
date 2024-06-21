package com.rentals.apartment.controller;

import com.rentals.apartment.domain.UserEntity;
import com.rentals.apartment.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    private final UserService userService;

    UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user")
    public UserEntity userByToken() {
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserEntity user) {
            return user;
        }
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserEntity> userById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.userById(id));
    }
}
