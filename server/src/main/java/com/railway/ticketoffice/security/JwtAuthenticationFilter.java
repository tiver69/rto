package com.railway.ticketoffice.security;

import com.railway.ticketoffice.domain.Passenger;
import com.railway.ticketoffice.validator.PassengerValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

import static com.railway.ticketoffice.security.Constants.HEADER_STRING;
import static com.railway.ticketoffice.security.Constants.TOKEN_PREFIX;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenProvider.class);

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private PassengerValidator passengerValidator;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = getJwtFromRequest(httpServletRequest);
            if (StringUtils.hasText(jwt) && jwtTokenProvider.validateToken(jwt)) {
                Long passengerId = jwtTokenProvider.getPassengerIdFromToken(jwt);
                Passenger passenger = passengerValidator.validateExistenceAndReturn(passengerId);
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        passenger, null, Collections.emptyList()); // list for roles here
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        } catch (Exception ex) {
            LOGGER.error("Could not set user authentication in security context");
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private String getJwtFromRequest(HttpServletRequest httpServletRequest) {
        String bearerToken = httpServletRequest.getHeader(HEADER_STRING);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(TOKEN_PREFIX)) {
            return bearerToken.substring(TOKEN_PREFIX.length(), bearerToken.length());
        }
        return null;
    }
}
