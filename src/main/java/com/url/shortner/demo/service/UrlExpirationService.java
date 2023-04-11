package com.url.shortner.demo.service;

import com.url.shortner.demo.entity.Url;
import com.url.shortner.demo.repository.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;



@Service
public class UrlExpirationService {

    @Autowired
    UrlService urlService;

    @Scheduled(fixedDelay = 24 * 60 * 60 * 1000) // run once a day
    public void expireLinks() {
        urlService.deleteExpiredUrls();
    }
}
