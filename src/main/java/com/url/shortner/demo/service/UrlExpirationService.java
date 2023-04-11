package com.url.shortner.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


/**
 * Schedular for expiring unused urls
 */
@Service
public class UrlExpirationService {

    @Autowired
    UrlServiceImpl urlService;

    @Scheduled(fixedDelay = 24 * 60 * 60 * 1000) // run once a day
    public void expireLinks() {
        urlService.deleteExpiredUrls();
    }
}
