package com.devnguyen.myshop.model.entity;

import com.devnguyen.myshop.enums.UserStatus;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;



@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "user")
public class User {

    @Id
    private String id;  

    @Indexed(unique = true)
    private String username;

   
    private String firstName;

  
    private String lastName;

  
    private String password;

    @Email(message = "Email is invalid")
    @NotBlank(message = "Email is required")
    @Indexed(unique = true)
    private String email;

   
    private UserStatus statusUser;

}
