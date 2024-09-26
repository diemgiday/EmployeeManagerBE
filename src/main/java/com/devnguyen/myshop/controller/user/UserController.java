package com.devnguyen.myshop.controller.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.devnguyen.myshop.model.entity.User;
import com.devnguyen.myshop.service.UserService;

import io.swagger.v3.oas.annotations.Operation;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;

    @Operation(summary = "Gets all user")
    @GetMapping("/")
    public List<User> getAllUser(){         
        return userService.getAllUser();
    }
    @Operation(summary = "Add user")
    @PostMapping("/")
    public String addUser(User user) {
        return userService.addUser(user);
    }
    @Operation(summary = "Edit user")
    @PutMapping()
    public void updateUser(){
        //todo
    }
    @Operation(summary = "Delete user")
    @DeleteMapping()
    public void removeUser(){
        //todo
    }
}
