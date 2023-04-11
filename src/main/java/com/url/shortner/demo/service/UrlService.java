package com.url.shortner.demo.service;

import com.url.shortner.demo.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

@Service
public interface UrlService {

    String shortenUrl(String originalUrl) ;

    String expandUrl(String shortUrl) throws ResourceNotFoundException ;

    void deleteExpiredUrls() ;


}