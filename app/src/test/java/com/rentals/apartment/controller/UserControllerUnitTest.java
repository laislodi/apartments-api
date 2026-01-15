package com.rentals.apartment.controller;

import com.rentals.apartment.domain.Role;
import com.rentals.apartment.domain.UserEntity;
import com.rentals.apartment.repositories.TokenRepository;
import com.rentals.apartment.repositories.UserRepository;
import com.rentals.apartment.service.UserService;
import org.hibernate.ObjectNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

public class UserControllerUnitTest {

    UserRepository userRepository = Mockito.mock(UserRepository.class);
    TokenRepository tokenRepository = Mockito.mock(TokenRepository.class);
    BCryptPasswordEncoder passwordEncoder = Mockito.mock(BCryptPasswordEncoder.class);
    UserService userService = new UserService(userRepository, tokenRepository, passwordEncoder);
    UserController userController = new UserController(userService);

    @Test
    void shouldFindTheRightUser() {
        Long id = 1L;
        UserEntity user = new UserEntity("bob_singer", "123456", "Bob", "Singer", "bob_singer@mail.com", Role.USER);

        Mockito.when(userRepository.findById(id)).thenReturn(Optional.of(user));

        // When
        ResponseEntity<UserEntity> response = userController.userById(id);

        // Then
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals("bob_singer", response.getBody().getUsername());
        Assertions.assertEquals("Bob", response.getBody().getFirstname());
        Assertions.assertEquals("Singer", response.getBody().getLastname());
        Assertions.assertEquals("bob_singer@mail.com", response.getBody().getEmail());
        Assertions.assertEquals(Role.USER, response.getBody().getRole());
    }

    @Test
    void shouldNotFindAnyUser() {
        Mockito.when(userRepository.findById(87L)).thenReturn(Optional.empty());

        // Then
        Assertions.assertThrows(ObjectNotFoundException.class,
                () -> userController.userById(87L));
    }
}
