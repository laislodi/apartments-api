package com.rentals.apartment.generator;

import com.rentals.apartment.domain.TokenEntity;
import com.rentals.apartment.domain.UserEntity;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;

public class TokenGenerator {

    private static final SecureRandom secureRandom = new SecureRandom();
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder();

    private static String generateNewToken() {
        byte[] randomBytes = new byte[24];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }

    public static TokenEntity generateToken(UserEntity user) {
        String token = TokenGenerator.generateNewToken();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, 1);
        Date oneHourFromNow = calendar.getTime();
        return new TokenEntity(token, oneHourFromNow, user);
    }
}
