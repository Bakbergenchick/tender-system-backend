package com.diplomaproject.tendersystembackend.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterDTO {
    private String username;
    private String surname;
    private String email;
    private String password;
}
