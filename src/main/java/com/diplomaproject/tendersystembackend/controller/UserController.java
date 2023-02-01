package com.diplomaproject.tendersystembackend.controller;

import com.diplomaproject.tendersystembackend.model.Users;
import com.diplomaproject.tendersystembackend.payload.LoginDTO;
import com.diplomaproject.tendersystembackend.payload.RegisterDTO;
import com.diplomaproject.tendersystembackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @GetMapping()
    public ResponseEntity<?> getAllUsers(){
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDTO registerDTO){
        Users new_user = userService.register(registerDTO.getUsername(), registerDTO.getSurname(), registerDTO.getEmail(), registerDTO.getPassword());

        return new ResponseEntity<>(new_user, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO){
        authenticate(loginDTO.getEmail(), loginDTO.getPassword());

        Users userByEmail = userService.findUserByEmail(loginDTO.getEmail());

        return new ResponseEntity<>(userByEmail.getEmail() + " you are successfully signed!", HttpStatus.OK);
    }

    private void authenticate(String email, String password) {
        Authentication authenticate;
        try{
            authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (Exception e){
            throw new BadCredentialsException("Invalid credentials!");
        }

        SecurityContextHolder.getContext().setAuthentication(authenticate);
    }
}
