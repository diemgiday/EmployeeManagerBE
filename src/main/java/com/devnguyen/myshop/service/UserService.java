package com.devnguyen.myshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devnguyen.myshop.model.entity.User;
import com.devnguyen.myshop.repository.UserRepo;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;

   
    public String addUser(User user){
        
        userRepo.save(user) ;
        return "Add user successfull";
    }
}
