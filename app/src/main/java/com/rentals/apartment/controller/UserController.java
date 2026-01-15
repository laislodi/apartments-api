package com.rentals.apartment.controller;

import com.rentals.apartment.domain.Role;
import com.rentals.apartment.domain.UserDTO;
import com.rentals.apartment.domain.UserEntity;
import com.rentals.apartment.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    private final UserService userService;

    UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    public UserDTO userByToken() {
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserEntity user) {
            return new UserDTO(user.getId(), user.getUsername(), user.getFirstname(), user.getLastname(), user.getEmail(), user.getRole());
        }
        return null;
    }

    @GetMapping("/role")
    public Role roleByToken() {
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserEntity user) {
            return user.getRole();
        }
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserEntity> userById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.userById(id));
    }

    @GetMapping("")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping("")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserEntity user) {
        return ResponseEntity.ok(userService.createUser(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserEntity user) {
        return ResponseEntity.ok(userService.updateUser(id, user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(id);
    }
}
