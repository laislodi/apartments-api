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
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserSpecifications userSpecifications;
    private final TokenRepository tokenRepository;
    private final TokenSpecifications tokenSpecifications;

    public UserService(UserRepository userRepository, UserSpecifications userSpecifications, TokenRepository tokenRepository, TokenSpecifications tokenSpecifications) {
        this.userRepository = userRepository;
        this.userSpecifications = userSpecifications;
        this.tokenRepository = tokenRepository;
        this.tokenSpecifications = tokenSpecifications;
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
        if (password.equals(user.get().getPassword())) {
            return createNewToken(user.get());
        }
        throw new Exception("The password is not correct");
    }

    public TokenEntity register(String username, String password, String firstname, String lastname, String email) {
        Role role = Role.USER;
        UserEntity user = new UserEntity(username, password, firstname, lastname, email, role);
        userRepository.save(user);
        return createNewToken(user);
    }

    public int logout(String token) {
        Optional<TokenEntity> optional = tokenRepository.findOne(Specification.where(
                tokenSpecifications.tokenEqualsTo(token)));
        if (optional.isPresent()) {
            Calendar cal = Calendar.getInstance();
            TokenEntity tokenEntity = optional.get();
            tokenEntity.setExpiredAt(cal.getTime());
            tokenRepository.save(tokenEntity);
            return 0;
        } else {
            System.out.println("logout -> NOT PRESENT");
            return 1;
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
