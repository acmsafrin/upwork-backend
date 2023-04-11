package com.url.shortner.demo.service;

import com.url.shortner.demo.repository.UrlRepository;
import com.url.shortner.demo.entity.Url;
import com.url.shortner.demo.util.UrlShortener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class UrlService {

    @Autowired
    private UrlRepository urlRepository;

    private final String BASE_URL = "https://example.com/";

    public String shortenUrl(String originalUrl) {
        Optional<Url> existingUrl = urlRepository.findByOriginalUrl(originalUrl);
        if (existingUrl.isPresent()) {
            return BASE_URL + existingUrl.get().getShortUrl();
        }

        String shortUrl = UrlShortener.shorten(originalUrl);
        Url url = new Url();
        url.setOriginalUrl(originalUrl);
        url.setShortUrl(shortUrl);
        url.setLastAccessedDate(Instant.now());
        url.setAccessCount(0);
        urlRepository.save(url);
        return BASE_URL + shortUrl;
    }

    public String expandUrl(String shortUrl) {
        Optional<Url> url = urlRepository.findByShortUrl(shortUrl);
        if (url.isPresent()) {
            Url urlEntity = url.get();
            urlEntity.setLastAccessedDate(Instant.now());
            urlEntity.setAccessCount(urlEntity.getAccessCount() + 1);
            urlRepository.save(urlEntity);
            return urlEntity.getOriginalUrl();
        }
        return null;
    }

    public void deleteExpiredUrls() {
        Instant expiryDate = Instant.now().minus(Duration.ofDays(30)); // Set retention period to 30 days
        List<Url> expiredUrls = urlRepository.findByLastAccessedDateBefore(expiryDate);
        urlRepository.deleteAll(expiredUrls);
    }

}