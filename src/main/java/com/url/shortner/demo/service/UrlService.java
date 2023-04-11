package com.url.shortner.demo.service;

import com.url.shortner.demo.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
public interface UrlService {

    String shortenUrl(String originalUrl) throws UnsupportedEncodingException;

    String expandUrl(String shortUrl) throws ResourceNotFoundException ;

    void deleteExpiredUrls() ;


}