package com.devnguyen.myshop.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.devnguyen.myshop.exception.AppException;
import com.devnguyen.myshop.exception.ErrorCode;
import com.devnguyen.myshop.model.dto.request.LoginRequestDTO;
import com.devnguyen.myshop.model.dto.request.RegisterRequestDTO;
import com.devnguyen.myshop.model.dto.response.TokenResponse;
import com.devnguyen.myshop.model.entity.RedisToken;
import com.devnguyen.myshop.model.entity.User;
import com.devnguyen.myshop.repository.RedisTokenRepo;
import com.devnguyen.myshop.repository.UserRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    
    private final UserRepo userRepo ;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;

    private final RedisTokenService redisTokenService;
    private final RedisTokenRepo redisTokenRepo;
    private final RedisTemplate<String, RedisToken> redisTemplate;

    public void register(RegisterRequestDTO registerRequestDTO) {
    
        // Check if username already exists
        Optional<User> exUser = userRepo.findByUsername(registerRequestDTO.getUsername());
        if (exUser.isPresent()) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        // Check if email already exists
        Optional<User> exEmail = userRepo.findByEmail(registerRequestDTO.getEmail());
        if (exEmail.isPresent()) {
             throw new AppException(ErrorCode.EMAIL_EXISTED);
        }
    
        // Send verification email 
        //
    
        // Hash the password
        String hashPassword = passwordEncoder.encode(registerRequestDTO.getPassword());
    
        // Create new user entity
        User user = new User();
        user.setUsername(registerRequestDTO.getUsername());
        user.setPassword(hashPassword);
        user.setEmail(registerRequestDTO.getEmail());
       
    
        // Save to database
        userRepo.save(user);
    }
    
    
    public TokenResponse login(LoginRequestDTO loginRequestDTO){
        // Check username
        Optional<User> userOptional = userRepo.findByUsername(loginRequestDTO.getUsername());
        if (!userOptional.isPresent()) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }

        User user = userOptional.get();

        // Check password
        if (!passwordEncoder.matches(loginRequestDTO.getPassword(), user.getPassword())) {
            throw new AppException(ErrorCode.PASSWORD_INCORRECT);
        }

        //genarate access_token save to Redis
        String accessToken = UUID.randomUUID().toString();

        redisTokenService.save(RedisToken.builder()
                                    .id(user.getUsername())
                                    .accessToken(accessToken)
                                    .build());
        return TokenResponse.builder()
                        .username(user.getUsername())
                        .accessToken(accessToken)
                        .build();
    }

    public void logout(){
        //todo
    }

    public String forgotPassword(String email){
        //check email
        Optional <User> userOptional = userRepo.findByEmail(email);
        if(!userOptional.isPresent()){
            throw new AppException(ErrorCode.EMAIL_NOT_EXISTED);
        }

        User user = userOptional.get();

        //genarate reset-token 
        String resetToken = UUID.randomUUID().toString();
        //save to redis
        RedisToken redisToken = RedisToken.builder()
                                .id(user.getUsername())
                                .resetToken(resetToken)
                                .build();
        redisTokenService.save(redisToken);
        System.out.println("Saved RedisToken: " + redisToken);

        return resetToken ;                     
    }  

    public String resetPassword(String resetToken, String newPassword) {

        // check token 
        Optional<RedisToken> tokenOptional = redisTokenRepo.findByResetToken(resetToken);
        if (!tokenOptional.isPresent()) {
            throw new AppException(ErrorCode.INVALID_TOKEN);
        }
        
        // get user to RedisToken
        RedisToken redisToken = tokenOptional.get();
        String username = redisToken.getId();  // id = username
        
        // get user to MongoDB
        Optional<User> userOptional = userRepo.findByUsername(username);
        if (!userOptional.isPresent()) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }
    
        // update password
        User user = userOptional.get();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepo.save(user);
    
        redisTemplate.delete(resetToken);
    
        return ("Password changed successful.");
    }
}

        

