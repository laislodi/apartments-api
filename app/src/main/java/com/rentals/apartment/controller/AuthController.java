package com.rentals.apartment.controller;

import com.rentals.apartment.controller.params.TokenRequestBody;
import com.rentals.apartment.domain.TokenEntity;
import com.rentals.apartment.domain.UserEntity;
import com.rentals.apartment.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping()
public class AuthController implements ControllerConfig {

    private final UserService userService;

    AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public TokenEntity login(@RequestBody UserEntity user) throws Exception {
        return userService.login(user.getUsername(), user.getPassword());
    }

    @PostMapping("/register")
    public TokenEntity register(@RequestBody UserEntity user) {
        return userService.register(user.getUsername(), user.getPassword(), user.getFirstname(), user.getLastname(), user.getEmail());
    }

    @PostMapping("/logout")
    public void logout(@RequestBody TokenRequestBody token) {
        userService.logout(token.getToken());
    }

    @PostMapping("/logout-all")
    public void logoutFromAll(@RequestBody TokenRequestBody token) {
        userService.logoutFromAll(token.getToken());
    }

}
