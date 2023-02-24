package com.diplomaproject.tendersystembackend.filter;

import com.diplomaproject.tendersystembackend.constants.SecurityConstant;
import com.diplomaproject.tendersystembackend.payload.HttpResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthEntryPoint extends Http403ForbiddenEntryPoint {


    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException arg2) throws IOException {
        HttpResponse httpResponse =
                new HttpResponse(
                        HttpStatus.FORBIDDEN.value(),
                        HttpStatus.FORBIDDEN,
                        HttpStatus.FOUND.getReasonPhrase().toUpperCase(),
                        SecurityConstant.FORBIDDEN_MESSAGE);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.sendError(HttpStatus.FORBIDDEN.value());

        ServletOutputStream outputStream = response.getOutputStream();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(outputStream, httpResponse);
        outputStream.flush();
    }
}
