package com.rentals.apartment.repositories.specifications;

import com.rentals.apartment.domain.TokenEntity;
import com.rentals.apartment.domain.UserEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class TokenSpecifications {
    public Specification<TokenEntity> tokenEqualsTo(String token) {
        return (tokenEntity, cq, cb) -> {
            String tok = tokenEntity.get("token").toString();
            System.out.println(tok);
            return cb.equal(tokenEntity.get("token"), token);
        };
    }

    public Specification<TokenEntity> userEqualsTo(Long userId) {
        return (tokenEntity, cq, cb) -> cb.equal(tokenEntity.get("user").get("id"), userId);
    }
}
