package com.diplomaproject.tendersystembackend.constants;

public class SecurityConstant {
    public static final long EXPIRATION_TIME = 320_000_000;
    public static final String TOKEN_HEADER = "Bearer ";
    public static final String JWT_TOKEN_HEADER = "Jwt-Header";
    public static final String TOKEN_CAN_NOT_BE_VERIFIED = "Token can not be verified";
    public static final String COMPANY = "ITSE-1901";
    public static final String AUTHORITIES = "Authorities";
    public static final String FORBIDDEN_MESSAGE = "You need to login to access to page";
    public static final String ACCESS_DENIED_MESSAGE = "You don't have a permission to the page";
    public static final String OPTIONS_HTTP_HEADER = "OPTIONS";
    public static final String[] PUBLIC_URLS = {"/auth/login", "/auth/register", "/auth/resetpassword/**", "/user/image"};
//    public static final String[] PUBLIC_URLS = { "**" };
}
