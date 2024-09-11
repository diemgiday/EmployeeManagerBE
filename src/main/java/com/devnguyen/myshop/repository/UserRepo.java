package com.devnguyen.myshop.repository;



import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.devnguyen.myshop.model.entity.User;

import java.util.Optional;


@Repository
public interface UserRepo extends MongoRepository<User, String>{
    
    Optional<User> findByEmail(String email);
    
    Optional<User> findByUsername(String username);

}
