package com.devnguyen.myshop.controller.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devnguyen.myshop.model.entity.User;
import com.devnguyen.myshop.repository.UserRepo;
import com.devnguyen.myshop.service.UserService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("users")
public class UserController {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    UserService userService;

    @GetMapping()
    public List<User> getAllUser(){         
        return userRepo.findAll();
    }

    @PostMapping()
    public ResponseEntity<String> addUser(@RequestBody User user) {
        try {
            userService.addUser(user);
            return ResponseEntity.ok("Thêm user thành công");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Lỗi khi thêm user: " + e.getMessage());
        }
    }
    
}
