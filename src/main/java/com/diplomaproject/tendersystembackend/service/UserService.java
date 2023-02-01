package com.diplomaproject.tendersystembackend.service;

import com.diplomaproject.tendersystembackend.model.Users;

import java.util.List;

public interface UserService {
    Users register(String username, String surname, String email, String password);
    Users findUserByName(String username);
    Users findUserByEmail(String email);
    List<Users> getAllUsers();
}
