package com.devnguyen.myshop.service;

import java.util.List;

import com.devnguyen.myshop.model.entity.User;

public interface UserService {

    List <User> getAllUser();

    String addUser(User user);
}
