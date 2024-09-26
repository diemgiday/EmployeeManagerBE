package com.devnguyen.myshop.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;

import com.devnguyen.myshop.model.entity.User;
import com.devnguyen.myshop.repository.UserRepo;
import com.devnguyen.myshop.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
  


    @Override
    public List<User> getAllUser() {
        return userRepo.findAll();
    }

    @Override
    public String addUser(User user) {
        
        userRepo.save(user);

        return "Add user successfull";
    }
}
