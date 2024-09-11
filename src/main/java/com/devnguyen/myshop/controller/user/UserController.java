package com.devnguyen.myshop.controller.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devnguyen.myshop.model.entity.User;
import com.devnguyen.myshop.service.UserService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/")
    public List<User> getAllUser(){         
        return userService.getAllUser();
    }

    @PostMapping("/")
    public String addUser(@RequestBody User user) {
        return userService.addUser(user);
    }
}
