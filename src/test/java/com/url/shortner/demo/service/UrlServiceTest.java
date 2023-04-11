package com.url.shortner.demo.service;

import com.url.shortner.demo.entity.Url;
import com.url.shortner.demo.repository.UrlRepository;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UrlServiceTest {

    @Autowired
    UrlRepository urlRepository;

    @Autowired
    UrlService urlService;

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