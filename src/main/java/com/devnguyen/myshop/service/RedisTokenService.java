package com.devnguyen.myshop.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.devnguyen.myshop.model.entity.RedisToken;
import com.devnguyen.myshop.repository.RedisTokenRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RedisTokenService {
    
    private final RedisTokenRepo redisTokenRepo;

    public void save(RedisToken token){
        redisTokenRepo.save(token);

    }
    public void delete(String id){
        redisTokenRepo.deleteById(id);
    }

    public Optional <RedisToken> getById (String id){
         return redisTokenRepo.findById(id);
    }
    
}
