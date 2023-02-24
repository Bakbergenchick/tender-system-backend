package com.diplomaproject.tendersystembackend.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.diplomaproject.tendersystembackend.constants.SecurityConstant;
import com.diplomaproject.tendersystembackend.payload.UserPrincipal;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {
    @Value("${jwt.secretKey}")
    private String jwtSecretKey;

    public String generateJwtToken(UserPrincipal userPrincipal){
        String[] claims = getClaimsFromUser(userPrincipal);

        return JWT.create()
                .withIssuer(SecurityConstant.COMPANY)
                .withIssuedAt(new Date())
                .withSubject(userPrincipal.getUsername())
                .withArrayClaim(SecurityConstant.AUTHORITIES, claims)
                .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstant.EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(jwtSecretKey.getBytes()));
    }

    public Set<GrantedAuthority> getAuthorities(String token){
        String[] claims = getClaimsFromToken(token);

        return Arrays.stream(claims)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }

    public Authentication getAuthentication(String userEmail,
                                            Set<GrantedAuthority> authorities,
                                            HttpServletRequest request){
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userEmail, null, authorities);

        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        return authenticationToken;
    }

    public boolean isTokenValid(String userEmail, String token){
        JWTVerifier jwtVerifier = getJwtVerifier();

        return StringUtils.isNotEmpty(userEmail) && !isTokenExpired(jwtVerifier, token);
    }

    private boolean isTokenExpired(JWTVerifier jwtVerifier, String token) {
        Date expiresAt = jwtVerifier.verify(token).getExpiresAt();

        return expiresAt.before(new Date());
    }

    public String getSubject(String token){
        JWTVerifier jwtVerifier = getJwtVerifier();

        return jwtVerifier.verify(token).getSubject();
    }


    private String[] getClaimsFromToken(String token) {
        JWTVerifier jwtVerifier = getJwtVerifier();

        return jwtVerifier.verify(token)
                .getClaim(SecurityConstant.AUTHORITIES)
                .asArray(String.class);
    }

    private JWTVerifier getJwtVerifier() {
        JWTVerifier jwtVerifier;

        try {
            Algorithm algorithm = Algorithm.HMAC512(jwtSecretKey.getBytes());
            jwtVerifier = JWT.require(algorithm)
                    .withIssuer(SecurityConstant.COMPANY)
                    .build();
        } catch (JWTVerificationException e) {
            throw new JWTVerificationException(SecurityConstant.TOKEN_CAN_NOT_BE_VERIFIED);
        }

        return jwtVerifier;
    }

    private String[] getClaimsFromUser(UserPrincipal userPrincipal) {
        List<String> authorities = new ArrayList<>();

        for (GrantedAuthority g :
                userPrincipal.getAuthorities()) {
            authorities.add(g.getAuthority());
        }

        return (authorities.toArray(new String[0]));
    }
}
