package com.rentals.apartment.controller;

import com.rentals.apartment.controller.params.TokenRequestBody;
import com.rentals.apartment.domain.AuthenticationResponse;
import com.rentals.apartment.domain.Role;
import com.rentals.apartment.domain.TokenEntity;
import com.rentals.apartment.domain.UserEntity;
import com.rentals.apartment.repositories.TokenRepository;
import com.rentals.apartment.repositories.UserRepository;
import com.rentals.apartment.repositories.specifications.TokenSpecifications;
import com.rentals.apartment.service.AuthService;
import com.rentals.apartment.service.JwtService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class AuthControllerUnitTest {

    UserRepository userRepository = Mockito.mock(UserRepository.class);
    JwtService jwtService = Mockito.mock(JwtService.class);
    BCryptPasswordEncoder passwordEncoder = Mockito.mock(BCryptPasswordEncoder.class);
    AuthenticationManager authenticationManager = Mockito.mock(AuthenticationManager.class);
    TokenRepository tokenRepository = Mockito.mock(TokenRepository.class);
    TokenSpecifications tokenSpecifications = Mockito.mock(TokenSpecifications.class);
    AuthService authService = new AuthService(userRepository, jwtService, tokenRepository, tokenSpecifications, passwordEncoder, authenticationManager);
    AuthController controller = new AuthController(authService);

    @Test
    void shouldLoginSuccessfully() {
        // login successful and not
        UserEntity user = new UserEntity();
        user.setUsername("bob_singer");
        user.setPassword("123456");
        UserEntity userEncrypted = new UserEntity("bob_singer", "Encrypted_Password", "Bob", "Singer", "bob_singer@mail.com", Role.USER);
        UserEntity savedUser = new UserEntity(userEncrypted.getUsername(), userEncrypted.getPassword(), userEncrypted.getFirstname(), userEncrypted.getLastname(), userEncrypted.getEmail(), userEncrypted.getRole());
        savedUser.setId(1L);
        String generatedToken = "Generated-Token-TOP-secret-123";

        Mockito.when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(savedUser));
        Mockito.when(jwtService.generateToken(savedUser)).thenReturn(generatedToken);

        // When
        ResponseEntity<AuthenticationResponse> response = controller.login(user);

        // Then
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(Role.USER, response.getBody().role());
        Assertions.assertEquals(generatedToken, response.getBody().token());
    }

    @Test
    void shouldNotLoginSuccessfully_WrongPassword() {
        UserEntity user = new UserEntity();
        user.setUsername("bob_singer");
        user.setPassword("Wrong_password");
        Mockito.when(authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()))
        ).thenThrow(BadCredentialsException.class);

        Assertions.assertThrows(BadCredentialsException.class,
                () -> controller.login(user));
    }

    @Test
    void shouldNotLoginSuccessfully_UserNotFound() {
        UserEntity user = new UserEntity();
        user.setUsername("bob_singer");
        user.setPassword("123456");

        Assertions.assertThrows(NoSuchElementException.class,
                () -> controller.login(user));
    }

    @Test
    void shouldRegisterSuccessfully() {
        // Given
        UserEntity user = new UserEntity("username", "password", "firstname", "lastname", "email", Role.USER);
        UserEntity userEncrypted = new UserEntity(user.getUsername(), "#password", user.getFirstname(), user.getLastname(), user.getEmail(), user.getRole());
        UserEntity savedUser = new UserEntity(userEncrypted.getUsername(), userEncrypted.getPassword(), userEncrypted.getFirstname(), userEncrypted.getLastname(), userEncrypted.getEmail(), userEncrypted.getRole());
        savedUser.setId(1L);
        Mockito.when(passwordEncoder.encode(user.getPassword())).thenReturn(userEncrypted.getPassword());
        Mockito.when(userRepository.save(userEncrypted)).thenReturn(savedUser);
        Mockito.when(jwtService.generateToken(savedUser)).thenReturn("new-token-with-1234");
        // When
        ResponseEntity<AuthenticationResponse> result = controller.register(user);
        // Then
        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assertions.assertNotNull(result.getBody());
        Assertions.assertEquals(user.getRole(), result.getBody().role());
        Assertions.assertEquals("new-token-with-1234", result.getBody().token());
    }

    @Test
    void shouldLogoutBecauseExpiredAtIsNull() {
        // logout successful and not
        // mock clock
        // Set the Mocked Time
        Clock clock = Mockito.mock();
        given(clock.instant()).willReturn(Instant.parse("2042-01-01T12:15:00Z"));
        given(clock.getZone()).willReturn(ZoneId.of("UCT"));
        UserEntity user = new UserEntity("bob_singer", "Encrypted_Password", "Bob", "Singer", "bob_singer@mail.com", Role.USER);
        String token = "SomE_R@nd0m_T0ken";
        TokenEntity tokenEntity = new TokenEntity(token, user);
        tokenEntity.setExpiredAt(null);

        Mockito.when(tokenRepository.findOne(Specification.where(
                tokenSpecifications.tokenEqualsTo("SomE_R@nd0m_T0ken")))).thenReturn(Optional.of(tokenEntity));

        TokenRequestBody body = new TokenRequestBody();
        body.setToken(token);
        // Call the service
        controller.logout(body);
        // mockito verify save token
        verify(tokenRepository).save(tokenEntity);
    }

    @Test
    void shouldLogoutBecauseExpiredAtIsWayInTheFuture() {
        Clock clock = Mockito.mock();
        given(clock.instant()).willReturn(Instant.parse("2042-01-01T12:15:00Z"));
        given(clock.getZone()).willReturn(ZoneId.of("UCT"));
        UserEntity user = new UserEntity("bob_singer", "Encrypted_Password", "Bob", "Singer", "bob_singer@mail.com", Role.USER);
        String token = "SomE_R@nd0m_T0ken";
        TokenEntity tokenEntity = new TokenEntity(token, user);
        tokenEntity.setExpiredAt(LocalDateTime.of(3024, 4, 3, 13, 2, 3));

        Mockito.when(tokenRepository.findOne(Specification.where(
                tokenSpecifications.tokenEqualsTo("SomE_R@nd0m_T0ken")))).thenReturn(Optional.of(tokenEntity));

        TokenRequestBody body = new TokenRequestBody();
        body.setToken(token);
        controller.logout(body);
        verify(tokenRepository, times(1)).save(tokenEntity);
    }
}
