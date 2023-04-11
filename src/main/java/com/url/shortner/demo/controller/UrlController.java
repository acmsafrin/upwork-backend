package com.url.shortner.demo.controller;

import com.url.shortner.demo.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UrlController {

    @Autowired
    private UrlService urlService;

    @PostMapping("/shorten")
    public String shortenUrl(@RequestBody String originalUrl) {
        return urlService.shortenUrl(originalUrl);
    }

    @GetMapping("/{shortUrl}")
    public ResponseEntity<Void> expandUrl(@PathVariable String shortUrl) {
        String originalUrl = urlService.expandUrl(shortUrl);
        if (originalUrl != null) {
            return ResponseEntity.status(HttpStatus.FOUND)
                    .header(HttpHeaders.LOCATION, originalUrl)
                    .build();
        }
        return ResponseEntity.notFound().build();
    }

}