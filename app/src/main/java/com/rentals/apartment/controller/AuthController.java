package com.rentals.apartment.controller;

import com.rentals.apartment.controller.params.TokenRequestBody;
import com.rentals.apartment.domain.AuthenticationResponse;
import com.rentals.apartment.domain.UserEntity;
import com.rentals.apartment.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping()
public class AuthController implements ControllerConfig {

    private final AuthService authService;

    AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody UserEntity user) {
        return ResponseEntity.ok(authService.authenticate(user));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody UserEntity user) {
        return ResponseEntity.ok(authService.register(user));
    }

    @PostMapping("/logout")
    public void logout(@RequestBody TokenRequestBody token) {
        authService.logout(token.getToken());
    }

//    @PostMapping("/logout-all")
//    public void logoutFromAll(@RequestBody TokenRequestBody token) {
//        authService.logoutFromAll(token.getToken());
//    }

//    @PostMapping("/token")
//    public String token(Authentication authentication) {
//        return tokenService.generateToken(authentication);
//    }
}
