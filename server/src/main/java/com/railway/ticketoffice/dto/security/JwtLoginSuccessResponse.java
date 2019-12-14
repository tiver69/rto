package com.railway.ticketoffice.dto.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtLoginSuccessResponse {
    private boolean success;
    private String token;
}
