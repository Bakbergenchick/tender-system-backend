package com.diplomaproject.tendersystembackend.filter;

import com.diplomaproject.tendersystembackend.constants.SecurityConstant;
import com.diplomaproject.tendersystembackend.utils.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        if(request.getMethod().equalsIgnoreCase(SecurityConstant.OPTIONS_HTTP_HEADER)){
            response.setStatus(HttpStatus.OK.value());
        } else{
            String header = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (header == null || !header.startsWith(SecurityConstant.TOKEN_HEADER)){
                filterChain.doFilter(request, response);
                return;
            }

            String token = header.substring(SecurityConstant.TOKEN_HEADER.length());
            String userEmail = jwtTokenProvider.getSubject(token);

            if (jwtTokenProvider.isTokenValid(userEmail, token)
                    && SecurityContextHolder.getContext().getAuthentication() == null){
                Set<GrantedAuthority> authorities = jwtTokenProvider.getAuthorities(token);
                Authentication authentication = jwtTokenProvider.getAuthentication(userEmail, authorities, request);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else{
                SecurityContextHolder.clearContext();
            }
        }

        filterChain.doFilter(request, response);
    }
}
