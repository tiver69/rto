package com.railway.ticketoffice.security;

import com.railway.ticketoffice.domain.Passenger;
import io.jsonwebtoken.*;
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

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            LOGGER.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            LOGGER.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            LOGGER.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            LOGGER.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            LOGGER.error("JWT claims string is empty");
        }
        return false;
    }

    public Long getPassengerIdFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
        String id = (String) claims.get("id");
        return Long.parseLong(id);
    }
}
