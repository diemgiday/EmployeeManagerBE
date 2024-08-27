package com.devnguyen.myshop.controller.auth;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devnguyen.myshop.model.dto.request.LoginRequestDTO;
import com.devnguyen.myshop.model.dto.request.RegisterRequestDTO;

import com.devnguyen.myshop.service.AuthenticationService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterRequestDTO registerRequestDTO) {
        authenticationService.register(registerRequestDTO);  
        return ResponseEntity.ok("Dang ky thanh cong");
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequestDTO loginRequestDTO) {
        authenticationService.login(loginRequestDTO);
        
        return "Dang nhap thanh cong";
    }

    @PostMapping("/forgot-password")
    public String forgotPassword(@RequestBody RegisterRequestDTO request) {
        //TODO: process POST request
        
        return "entity";
    }

    @PostMapping("/logout")
    public String logout() {
        
        return "Dang xuat ";
    }

    

}
