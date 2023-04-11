package com.url.shortner.demo.service;

import com.url.shortner.demo.entity.Url;
import com.url.shortner.demo.repository.UrlRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UrlServiceTest {

    @Autowired
    UrlRepository urlRepository;

    @Autowired
    UrlServiceImpl urlService;

    @Test
    void deleteExpiredUrls() {
        Instant dateBefore2Days = Instant.now().minus(Duration.ofDays(2));
        Url url1=Url.builder().originalUrl("t1")
                .shortUrl("s1")
                .lastAccessedDate(dateBefore2Days)
                .build();

        Url url2=Url.builder().originalUrl("t2")
                .shortUrl("s2")
                .lastAccessedDate(dateBefore2Days)
                .build();

        urlRepository.save(url1);
        urlRepository.save(url2);

        urlService.deleteExpiredUrls();

        assertTrue(urlRepository.findByShortUrl("s1").get().isEmpty());
        assertTrue(urlRepository.findByShortUrl("s2").get().isEmpty());

    }
}