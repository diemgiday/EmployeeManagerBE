package com.devnguyen.myshop.model.dto.request;

import org.springframework.data.mongodb.core.index.Indexed;

import com.devnguyen.myshop.enums.UserStatus;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class UserRequestDTO {
    
    @NotNull(message = "username must be not null")
    private String username;

    @NotNull(message = "firstName must be not null")
    private String firstName;

    @NotNull(message = "lastName must be not null")
    private String lastName;

    @NotNull(message = "password must be not null")
    private String password;

    @Email(message = "Email is invalid")
    @NotBlank(message = "Email is required")
    @Indexed(unique = true)
    private String email;

    private UserStatus statusUser = UserStatus.active;


}
