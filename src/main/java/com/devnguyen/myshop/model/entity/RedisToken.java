package com.devnguyen.myshop.model.entity;

import lombok.*;


import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serializable;
import java.time.Instant;


@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@RedisHash("RedisToken")
public class RedisToken implements Serializable {
    
    private String id;

    private String accessToken;

    private String refreshToken;

    @Indexed
    private String resetToken;

    private Instant expiryTime;

    public boolean isExpired() {
        return expiryTime != null && Instant.now().isAfter(expiryTime);
    }
}
