package com.devnguyen.myshop.controller.auth;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.devnguyen.myshop.model.dto.request.LoginRequestDTO;
import com.devnguyen.myshop.model.dto.request.RegisterRequestDTO;
import com.devnguyen.myshop.model.dto.response.TokenResponse;
import com.devnguyen.myshop.service.AuthenticationService;

import jakarta.validation.Valid;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/auth")

public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/register")
    public String register(@RequestBody @Valid RegisterRequestDTO registerRequestDTO) {
        authenticationService.register(registerRequestDTO);  
        return "Regiter successfull";
    }
 
    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequestDTO request) {
        TokenResponse tokenResponse = authenticationService.login(request);
        return new ResponseEntity<>(tokenResponse, HttpStatus.OK);
    }


    @PostMapping("/forgot-password")
    public String forgotPassword(@RequestParam String email) {
        return  authenticationService.forgotPassword(email);
        
    }

    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam String resetToken, @RequestParam String newPassword) {
        return authenticationService.resetPassword(resetToken, newPassword);
    }

    @PostMapping("/logout")
    public String logout() {
        
        return "Dang xuat ";
    }

    

}
