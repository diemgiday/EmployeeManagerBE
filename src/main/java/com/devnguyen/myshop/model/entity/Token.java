package com.devnguyen.myshop.model.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "token")
public class Token  {

    
    private String username; 

  
    private String accessToken;


    private String refreshToken;

  
    private String resetToken;

}
