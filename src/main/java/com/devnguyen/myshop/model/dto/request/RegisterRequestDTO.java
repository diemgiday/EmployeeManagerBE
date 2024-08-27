package com.devnguyen.myshop.model.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class RegisterRequestDTO {
    @NotBlank(message = "Username must be not blank")
    private String username;

    @NotNull(message = "Password must be not null")
    private String password;
 
    @Email(message = "Invalid email address")
    private String email;
}
