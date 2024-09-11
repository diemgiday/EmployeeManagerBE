package com.devnguyen.myshop.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.devnguyen.myshop.model.entity.RedisToken;
import java.util.Optional;


@Repository
public interface RedisTokenRepo extends CrudRepository<RedisToken, String>{

    Optional<RedisToken> findByResetToken(String resetToken);
    
}


