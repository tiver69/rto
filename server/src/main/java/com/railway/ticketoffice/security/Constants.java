package com.railway.ticketoffice.security;

public class Constants {

    public static final String SING_UP_URLS = "/api/**";
    public static final String SECRET = "SecretKeyToGenJWTs";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final Long EXPIRATION_TIME = 300_000L; //30 minutes
}
