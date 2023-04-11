package com.url.shortner.demo.service;

import com.url.shortner.demo.exception.ResourceNotFoundException;
import com.url.shortner.demo.repository.UrlRepository;
import com.url.shortner.demo.entity.Url;
import com.url.shortner.demo.util.UrlShortener;
import com.url.shortner.demo.util.UrlShortenerV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UrlService {

    @Autowired
    private UrlRepository urlRepository;

    @Autowired
    private RedisCacheService redisCacheService;



    @Value("${base.url}")
    private String BASE_URL;

    @Value("${retention.period.days}")
    private long RETENTION_PERIOD_DAYS;


    /**
     * Generate new shorten URL if the is no record exist already.
     * The shorten url record will be persisted in DB
     * @param originalUrl
     * @return
     * @throws NoSuchAlgorithmException
     */
    public String shortenUrl(String originalUrl) throws NoSuchAlgorithmException {
        Optional<Url> existingUrl = urlRepository.findByOriginalUrl(originalUrl);
        if (existingUrl.isPresent()) {
            return BASE_URL + existingUrl.get().getShortUrl();
        }

        String shortUrl = UrlShortener.shorten(originalUrl);
        Url url = saveUrl(originalUrl, shortUrl);

        //Add to the cache (REDIS)
        redisCacheService.put(shortUrl,url);

        return BASE_URL + shortUrl;
    }


    /**
     * Check shotUrl exist in cache or DB &
     * @param shortUrl
     * @return
     */
    public String expandUrl(String shortUrl) {
        //Key the url from Cache
        Url cache=  redisCacheService.get(shortUrl);
        if(cache!=null){
            Url urlDB=urlRepository.findById(cache.getId()).get();
            updateAccess(urlDB);
            return cache.getOriginalUrl();
        }

        Optional<List<Url>> url = urlRepository.findByShortUrl(shortUrl);
        if (url.isPresent()) {
            Url urlEntity = url.get().get(0);
            updateAccess(urlEntity);
            return urlEntity.getOriginalUrl();
        }

        //TODO
        //Throw not found exception
        throw new ResourceNotFoundException("The url is not found "+shortUrl);

    }


    /**
     * This method will be used to clear expired records.
     * This will be called by schedular.
     */
    public void deleteExpiredUrls() {
        Instant expiryDate = Instant.now().minus(Duration.ofDays(RETENTION_PERIOD_DAYS));

        //Remove from DB
        List<Url> expiredUrls = urlRepository.findByLastAccessedDateBefore(expiryDate);
        urlRepository.deleteAll(expiredUrls);

        //No need to remove from cache as expiry time is set when setting cache.

    }


    private void updateAccess(Url url) {

        url.setLastAccessedDate(Instant.now());
        urlRepository.save(url);
        //Update Cache
        redisCacheService.put(url.getShortUrl(),url);
    }


    private Url saveUrl(String originalUrl, String shortUrl) {
        Url url = new Url();
        url.setOriginalUrl(originalUrl);
        url.setShortUrl(shortUrl);
        url.setLastAccessedDate(Instant.now());
        urlRepository.save(url);
        return url;
    }

}