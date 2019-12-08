package com.railway.ticketoffice.dto.security;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvalidLoginResponse {
    private String login;
    private String password;

    public InvalidLoginResponse() {
        login = "Invalid login";
        password ="Invalid password";
    }
}
