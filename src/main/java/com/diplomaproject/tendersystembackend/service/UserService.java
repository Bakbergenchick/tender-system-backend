package com.diplomaproject.tendersystembackend.service;

import com.diplomaproject.tendersystembackend.model.Users;

import java.util.List;

public interface UserService {
    Users register(String firstname, String surname, String patronymic, String email, String password);
    Users findUserByName(String username);
    Users findUserByEmail(String email);
    List<Users> getAllUsers();
    void saveUserVerificationToken(Users theUsers, String verificationToken);
    String validateToken(String theToken);
}
