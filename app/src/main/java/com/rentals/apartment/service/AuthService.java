package com.rentals.apartment.service;

import com.rentals.apartment.domain.AuthenticationResponse;
import com.rentals.apartment.domain.TokenEntity;
import com.rentals.apartment.domain.UserEntity;
import com.rentals.apartment.repositories.TokenRepository;
import com.rentals.apartment.repositories.specifications.TokenSpecifications;
import com.rentals.apartment.repositories.UserRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    private final TokenRepository tokenRepository;
    private final TokenSpecifications tokenSpecifications;

    public AuthService(
            UserRepository userRepository,
            JwtService jwtService,
            TokenRepository tokenRepository,
            TokenSpecifications tokenSpecifications,
            BCryptPasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.tokenRepository = tokenRepository;
        this.tokenSpecifications = tokenSpecifications;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationResponse register(UserEntity request) {
        UserEntity user = new UserEntity();
        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());
        UserEntity savedUser = userRepository.save(user);

        String token = jwtService.generateToken(savedUser);
        return new AuthenticationResponse(token, savedUser.getRole());
    }

    public AuthenticationResponse authenticate(UserEntity request) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        UserEntity user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        String token = jwtService.generateToken(user);
        return new AuthenticationResponse(token, user.getRole());
    }

//    private TokenEntity createNewToken(UserEntity user) {
//        TokenEntity tokenEntity = new TokenEntity(jwtService.generateToken(user), user);
//        return tokenRepository.save(tokenEntity);
//    }

//    public TokenEntity login(String username, String password) throws Exception {
//        Optional<UserEntity> user = userRepository.findOne(Specification.where(userSpecifications.usernameEqualsTo(username)));
//        if (user.isEmpty()) {
//            throw new ObjectNotFoundException("User", user);
//        }
//        if (passwordEncoder.matches(password, user.get().getPassword())) {
//            return createNewToken(user.get());
//        }
//        throw new Exception("The password is not correct");
//    }

//    public TokenEntity register(String username, String password, String firstname, String lastname, String email) {
//        Role role = Role.USER;
//        UserEntity user = new UserEntity(username, passwordEncoder.encode(password), firstname, lastname, email, role);
//        userRepository.save(user);
//        return createNewToken(user);
//    }

    public void logout(String token) {
        Optional<TokenEntity> optional = tokenRepository.findOne(Specification.where(
                tokenSpecifications.tokenEqualsTo(token)));
        if (optional.isPresent()) {
            LocalDateTime now = LocalDateTime.now();
            if (Objects.isNull(optional.get().getExpiredAt()) || optional.get().getExpiredAt().isAfter(now)){
                TokenEntity tokenEntity = optional.get();
                tokenEntity.setExpiredAt(now);
                tokenRepository.save(tokenEntity);
            }
        } else {
            // Log something maybe
            System.out.println("logout -> NOT PRESENT");
        }
    }

//    public void logoutFromAll(String token) {
//        Optional<TokenEntity> optional = tokenRepository.findOne(Specification.where(
//                tokenSpecifications.tokenEqualsTo(token)
//        ));
//        if (optional.isPresent()) {
//            Long userId = optional.get().getUserEntity().getId();
//            List<TokenEntity> allUserTokens = tokenRepository.findAll(Specification.where(
//                    tokenSpecifications.userEqualsTo(userId)
//            ));
//            for (TokenEntity tk:
//                 allUserTokens) {
//                Date now = Calendar.getInstance().getTime();
//                tk.setExpiredAt(now);
//                tokenRepository.save(tk);
//            }
//        } else {
//            System.out.println("logoutFromAll -> NOT PRESENT");
//        }
//    }

}
