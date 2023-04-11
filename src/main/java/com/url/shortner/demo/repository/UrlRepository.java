package com.url.shortner.demo.repository;

import com.url.shortner.demo.entity.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface UrlRepository extends MongoRepository<Url, String> {

    Optional<List<Url>> findByShortUrl(String shortUrl);

    Optional<Url> findByOriginalUrl(String originalUrl);

    List<Url> findByLastAccessedDateBefore(Instant expiryDate);

}