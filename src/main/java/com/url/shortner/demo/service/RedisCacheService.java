package com.url.shortner.demo.service;


import com.url.shortner.demo.entity.Url;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;


@Service
public class RedisCacheService {

    @Autowired
    private RedisTemplate redisTemplate;

    public static final String HASH_KEY = "SHORTEN-URLS";

    @Value("${retention.period.days}")
    private long RETENTION_PERIOD_DAYS;


    public void put(String key, Url url){
        redisTemplate.opsForHash().put(HASH_KEY,key,url);
        Instant expiryDate = Instant.now().plus(Duration.ofDays(RETENTION_PERIOD_DAYS));
        redisTemplate.opsForHash().getOperations().expireAt(key,expiryDate);
    }

    public Url get(String key){
        return (Url) redisTemplate.opsForHash().entries(HASH_KEY).get(key);
    }


}
