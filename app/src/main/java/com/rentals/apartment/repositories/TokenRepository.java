package com.rentals.apartment.repositories;

import com.rentals.apartment.domain.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<TokenEntity, String>, JpaSpecificationExecutor<TokenEntity> {

    TokenEntity findByToken(String token);
}
