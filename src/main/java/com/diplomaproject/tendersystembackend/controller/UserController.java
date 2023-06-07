package com.diplomaproject.tendersystembackend.controller;

import com.diplomaproject.tendersystembackend.constants.SecurityConstant;
import com.diplomaproject.tendersystembackend.event.RegistrationCompleteEvent;
import com.diplomaproject.tendersystembackend.model.Users;
import com.diplomaproject.tendersystembackend.model.VerificationToken;
import com.diplomaproject.tendersystembackend.payload.LoginDTO;
import com.diplomaproject.tendersystembackend.payload.RegisterDTO;
import com.diplomaproject.tendersystembackend.payload.UserPrincipal;
import com.diplomaproject.tendersystembackend.repo.DocumentRepository;
import com.diplomaproject.tendersystembackend.repo.VerificationTokenRepository;
import com.diplomaproject.tendersystembackend.service.UserService;
import com.diplomaproject.tendersystembackend.utils.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@CrossOrigin("*")
public class UserController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final ApplicationEventPublisher publisher;
    private final VerificationTokenRepository tokenRepository;

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<?> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDTO registerDTO, final HttpServletRequest request) {
        Users new_user = userService.register(
                registerDTO.getFirstname(),
                registerDTO.getSurname(),
                registerDTO.getPatronymic(),
                registerDTO.getEmail(),
                registerDTO.getPassword());
        publisher.publishEvent(new RegistrationCompleteEvent(new_user, applicationUrl(request)));
        return new ResponseEntity<>(new_user, HttpStatus.CREATED);
    }

    @GetMapping("/register/verifyEmail")
    public String verifyEmail(@RequestParam("token") String token) {
        VerificationToken theToken = tokenRepository.findByToken(token);

        if (theToken.getUsers().isActive()) {
            return "This account has already been verified, please, login.";
        }

        String verificationResult = userService.validateToken(token);
        if (verificationResult.equalsIgnoreCase("valid")) {
            return "Email verified successfully. Now you can login to your account";
        }

        return "Invalid verification token";
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        Users userByEmail = userService.findUserByEmail(loginDTO.getEmail());
        if (!userByEmail.isActive()) {
            return new ResponseEntity<>("You are not confirmed email!", HttpStatus.FORBIDDEN);
        }
        authenticate(loginDTO.getEmail(), loginDTO.getPassword());
        UserPrincipal userPrincipal = new UserPrincipal(userByEmail);
        HttpHeaders httpHeaders = getJwtHeader(userPrincipal);
        return new ResponseEntity<>(userByEmail.getEmail() + " you are successfully signed!", httpHeaders, HttpStatus.OK);
    }

    private HttpHeaders getJwtHeader(UserPrincipal userPrincipal) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(SecurityConstant.JWT_TOKEN_HEADER, jwtTokenProvider.generateJwtToken(userPrincipal));

        return httpHeaders;
    }

    private void authenticate(String email, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
    }

    public String applicationUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }
}
