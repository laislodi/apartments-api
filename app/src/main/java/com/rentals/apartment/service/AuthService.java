package com.rentals.apartment.service;

import com.rentals.apartment.domain.Role;
import com.rentals.apartment.domain.TokenEntity;
import com.rentals.apartment.domain.UserEntity;
import com.rentals.apartment.generator.TokenGenerator;
import com.rentals.apartment.repositories.TokenRepository;
import com.rentals.apartment.repositories.specifications.TokenSpecifications;
import com.rentals.apartment.repositories.UserRepository;
import com.rentals.apartment.repositories.specifications.UserSpecifications;
import org.hibernate.ObjectNotFoundException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final UserSpecifications userSpecifications;
    private final TokenRepository tokenRepository;
    private final TokenSpecifications tokenSpecifications;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, UserSpecifications userSpecifications, TokenRepository tokenRepository, TokenSpecifications tokenSpecifications, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userSpecifications = userSpecifications;
        this.tokenRepository = tokenRepository;
        this.tokenSpecifications = tokenSpecifications;
        this.passwordEncoder = passwordEncoder;
    }

    private TokenEntity createNewToken(UserEntity user) {
        TokenEntity tokenEntity = TokenGenerator.generateToken(user);
        return tokenRepository.save(tokenEntity);
    }

    public TokenEntity login(String username, String password) throws Exception {
        Optional<UserEntity> user = userRepository.findOne(Specification.where(userSpecifications.usernameEqualsTo(username)));
        if (user.isEmpty()) {
            throw new ObjectNotFoundException("User", user);
        }
        if (passwordEncoder.matches(password, user.get().getPassword())) {
            return createNewToken(user.get());
        }
        throw new Exception("The password is not correct");
    }

    public TokenEntity register(String username, String password, String firstname, String lastname, String email) {
        Role role = Role.USER;
        UserEntity user = new UserEntity(username, passwordEncoder.encode(password), firstname, lastname, email, role);
        userRepository.save(user);
        return createNewToken(user);
    }

    public void logout(String token) {
        Optional<TokenEntity> optional = tokenRepository.findOne(Specification.where(
                tokenSpecifications.tokenEqualsTo(token)));
        if (optional.isPresent()) {
            Calendar cal = Calendar.getInstance();
            Date now = cal.getTime();
            if (optional.get().getExpiredAt().after(now)){
                TokenEntity tokenEntity = optional.get();
                tokenEntity.setExpiredAt(cal.getTime());
                tokenRepository.save(tokenEntity);
            }
        } else {
            // Log something maybe
            System.out.println("logout -> NOT PRESENT");
        }
    }

    public void logoutFromAll(String token) {
        Optional<TokenEntity> optional = tokenRepository.findOne(Specification.where(
                tokenSpecifications.tokenEqualsTo(token)
        ));
        if (optional.isPresent()) {
            Long userId = optional.get().getUserEntity().getId();
            List<TokenEntity> allUserTokens = tokenRepository.findAll(Specification.where(
                    tokenSpecifications.userEqualsTo(userId)
            ));
            for (TokenEntity tk:
                 allUserTokens) {
                Date now = Calendar.getInstance().getTime();
                tk.setExpiredAt(now);
                tokenRepository.save(tk);
            }
        } else {
            System.out.println("logoutFromAll -> NOT PRESENT");
        }
    }

}
