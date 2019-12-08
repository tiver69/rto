package com.railway.ticketoffice.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.railway.ticketoffice.dto.security.InvalidLoginResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This class is called whenever an exception is thrown - user trying
 * to access recourse requires authentication
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                         AuthenticationException e) throws IOException, ServletException {
        InvalidLoginResponse invalidLoginResponse = new InvalidLoginResponse();
        String jsonInvalidLoginResponse = new ObjectMapper().writeValueAsString(invalidLoginResponse);

        httpServletResponse.setContentType("application/json");
        httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        httpServletResponse.getWriter().print(jsonInvalidLoginResponse);
    }
}
