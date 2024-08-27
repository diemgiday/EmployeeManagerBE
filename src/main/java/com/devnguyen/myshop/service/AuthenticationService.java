package com.devnguyen.myshop.service;

import java.util.Optional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.devnguyen.myshop.model.dto.request.LoginRequestDTO;
import com.devnguyen.myshop.model.dto.request.RegisterRequestDTO;
import com.devnguyen.myshop.model.entity.User;
import com.devnguyen.myshop.repository.UserRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    
    private final UserRepo userRepo ;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;
    

    public void register(RegisterRequestDTO registerRequestDTO){
        //validation email
        if (!isValidEmail(registerRequestDTO.getEmail())) {
            throw new IllegalArgumentException("Invalid email address");
        }
        //check email and username
        Optional <User> exEmail = userRepo.findByEmail(registerRequestDTO.getEmail());
        if(exEmail.isPresent()){
            throw new IllegalArgumentException("Email is already used");
        }
        Optional <User> exUser = userRepo.findByUsername(registerRequestDTO.getUsername());
        if(exUser.isPresent()){
            throw new IllegalArgumentException("Username is already used");
        }
        //send mail
        mailService.sendMail(registerRequestDTO.getEmail(), "Verify account", "Click link : abc to confirm");

        //verify how??
        
        //hash password
        String hashPassword = passwordEncoder.encode(registerRequestDTO.getPassword());

        //create new user entity
        User user = new User();

        user.setUsername(registerRequestDTO.getUsername());
        user.setPassword(hashPassword);
        user.setEmail(registerRequestDTO.getEmail());

        //save db
        userRepo.save(user);
    }

    private boolean isValidEmail(String email) {
        return email != null && email.contains("@") && email.contains(".");
    }
    
    public void login(LoginRequestDTO loginRequestDTO){
        // Check username
        Optional<User> userOptional = userRepo.findByUsername(loginRequestDTO.getUsername());
        if (!userOptional.isPresent()) {
            throw new IllegalArgumentException("Invalid username or password");
        }

        User user = userOptional.get();

        // Check password
        if (!passwordEncoder.matches(loginRequestDTO.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid username or password");
        }

        //genarate access_token
        
    }

    public void logout(){
        //todo
    }

    public void forgotPassword(){
        //check email

        //send email

        //IsValid and IsExpire reset-token

        //changed password
    }
}
