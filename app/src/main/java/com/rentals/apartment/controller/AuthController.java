package com.rentals.apartment.controller;

import com.rentals.apartment.controller.params.TokenRequestBody;
import com.rentals.apartment.domain.TokenEntity;
import com.rentals.apartment.domain.UserEntity;
import com.rentals.apartment.service.AuthService;
import com.rentals.apartment.service.TokenServiceExample;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping()
public class AuthController implements ControllerConfig {

    private final AuthService authService;
    private final TokenServiceExample tokenService;

    AuthController(AuthService authService, TokenServiceExample tokenService) {
        this.authService = authService;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public TokenEntity login(@RequestBody UserEntity user) throws Exception {
        return authService.login(user.getUsername(), user.getPassword());
    }

    @PostMapping("/register")
    public TokenEntity register(@RequestBody UserEntity user) {
        return authService.register(user.getUsername(), user.getPassword(), user.getFirstname(), user.getLastname(), user.getEmail());
    }

    @PostMapping("/logout")
    public void logout(@RequestBody TokenRequestBody token) {
        authService.logout(token.getToken());
    }

    @PostMapping("/logout-all")
    public void logoutFromAll(@RequestBody TokenRequestBody token) {
        authService.logoutFromAll(token.getToken());
    }

    @PostMapping("/token")
    public String token(Authentication authentication) {
        return tokenService.generateToken(authentication);
    }
}
