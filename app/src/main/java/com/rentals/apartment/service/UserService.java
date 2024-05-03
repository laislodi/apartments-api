package com.rentals.apartment.service;

import com.rentals.apartment.domain.TokenEntity;
import com.rentals.apartment.domain.UserEntity;
import com.rentals.apartment.repositories.TokenRepository;
import com.rentals.apartment.repositories.UserRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;

    public UserService(UserRepository userRepository, TokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
    }

    public UserEntity userByToken(String token) throws Exception {
        TokenEntity tokenEntity = tokenRepository.findByToken(token);
        Optional<UserEntity> user = userRepository.findById(tokenEntity.getUserEntity().getId());
        if (user.isEmpty()) {
            throw new ObjectNotFoundException(UserEntity.class, "The token is not associated with any users");
        }
        return user.get();
    }

    public UserEntity userById(Long id) throws Exception {
        Optional<UserEntity> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new ObjectNotFoundException(UserEntity.class, "There is no user with the id: %s".formatted(id));
        }
        return user.get();
    }
}
