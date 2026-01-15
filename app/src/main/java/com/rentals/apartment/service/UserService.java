package com.rentals.apartment.service;

import com.rentals.apartment.domain.Role;
import com.rentals.apartment.domain.TokenEntity;
import com.rentals.apartment.domain.UserDTO;
import com.rentals.apartment.domain.UserEntity;
import com.rentals.apartment.repositories.TokenRepository;
import com.rentals.apartment.repositories.UserRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, TokenRepository tokenRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserEntity userByToken(String token) throws ObjectNotFoundException {
        TokenEntity tokenEntity = tokenRepository.findByToken(token);
        Optional<UserEntity> user = userRepository.findById(tokenEntity.getUserEntity().getId());
        if (user.isEmpty()) {
            throw new ObjectNotFoundException(UserEntity.class, "The token is not associated with any users");
        }
        return user.get();
    }

    public UserEntity userById(Long id) throws ObjectNotFoundException {
        Optional<UserEntity> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new ObjectNotFoundException(UserEntity.class, "There is no user with the id: %s".formatted(id));
        }
        return user.get();
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> new UserDTO(
                        user.getId(),
                        user.getUsername(),
                        user.getFirstname(),
                        user.getLastname(),
                        user.getEmail(),
                        user.getRole()))
                .toList();
    }

    public UserDTO createUser(UserEntity user) {
        if (user.getUsername() == null || user.getUsername().isEmpty()) {
            throw new IllegalArgumentException("Username is required");
        }
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password is required");
        }
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email is required");
        }
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (user.getRole() == null) {
            user.setRole(Role.USER);
        }

        UserEntity savedUser = userRepository.save(user);
        return new UserDTO(
                savedUser.getId(),
                savedUser.getUsername(),
                savedUser.getFirstname(),
                savedUser.getLastname(),
                savedUser.getEmail(),
                savedUser.getRole());
    }

    public UserDTO updateUser(Long id, UserEntity updatedUser) {
        UserEntity existingUser = userById(id);

        if (updatedUser.getFirstname() != null) {
            existingUser.setFirstname(updatedUser.getFirstname());
        }
        if (updatedUser.getLastname() != null) {
            existingUser.setLastname(updatedUser.getLastname());
        }
        if (updatedUser.getEmail() != null) {
            existingUser.setEmail(updatedUser.getEmail());
        }
        if (updatedUser.getRole() != null) {
            existingUser.setRole(updatedUser.getRole());
        }
        if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }

        UserEntity savedUser = userRepository.save(existingUser);
        return new UserDTO(
                savedUser.getId(),
                savedUser.getUsername(),
                savedUser.getFirstname(),
                savedUser.getLastname(),
                savedUser.getEmail(),
                savedUser.getRole());
    }

    public void deleteUser(Long id) {
        UserEntity user = userById(id);
        userRepository.delete(user);
    }
}
