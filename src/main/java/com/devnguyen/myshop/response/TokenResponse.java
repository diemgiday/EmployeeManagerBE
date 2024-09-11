package com.devnguyen.myshop.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
@AllArgsConstructor
public class TokenResponse {

    private String username ;

    private String accessToken;
    
}
