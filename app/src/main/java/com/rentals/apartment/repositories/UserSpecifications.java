package com.rentals.apartment.repositories;

import com.rentals.apartment.domain.UserEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class UserSpecifications {

    public Specification<UserEntity> usernameEqualsTo(String username) {
        return (user, cq, cb) -> cb.equal(user.get("username"), username);
    }
}
