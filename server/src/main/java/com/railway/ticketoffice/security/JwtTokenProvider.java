package com.railway.ticketoffice.security;

import com.railway.ticketoffice.domain.Passenger;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.railway.ticketoffice.security.Constants.EXPIRATION_TIME;
import static com.railway.ticketoffice.security.Constants.SECRET;

/**
 * Generates, validate and gets passengerId from the token
 */
@Component
public class JwtTokenProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenProvider.class);

    public String generateToken(Authentication authentication) {
        Passenger passenger = (Passenger) authentication.getPrincipal();

        Date now = new Date(System.currentTimeMillis());
        Date expirationDate = new Date(now.getTime() + EXPIRATION_TIME);
        String passengerId = Long.toString(passenger.getId());

        Map<String, Object> claims = new HashMap<>();
        claims.put("id", passengerId);
        claims.put("login", passenger.getLogin());
        claims.put("firstName", passenger.getFirstName());

        LOGGER.debug("Request for login from passenger {} - token was provided at {}", passenger.getLogin(), now);
        return Jwts.builder()
                .setSubject(passengerId)
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }
}
