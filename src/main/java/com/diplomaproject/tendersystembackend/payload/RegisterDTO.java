package com.diplomaproject.tendersystembackend.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterDTO {
    private String firstname;
    private String surname;
    private String patronymic;
    private String email;
    private String password;
}
