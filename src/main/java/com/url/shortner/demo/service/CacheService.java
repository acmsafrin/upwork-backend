package com.url.shortner.demo.service;


import com.url.shortner.demo.entity.Url;
import org.springframework.stereotype.Service;


@Service
public interface CacheService {

    void put(String key, Url url);

    Url get(String key);


}
