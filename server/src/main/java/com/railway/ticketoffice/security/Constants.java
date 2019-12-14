package com.railway.ticketoffice.security;

import java.util.Arrays;
import java.util.List;

public class Constants {

    public static final List<String> SING_UP_URLS = Arrays.asList("/api/passenger/login", "/api/passenger/register");
    public static final String SECRET = "SecretKeyToGenJWTs";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final Long EXPIRATION_TIME = 300_000L; //30 minutes
}
