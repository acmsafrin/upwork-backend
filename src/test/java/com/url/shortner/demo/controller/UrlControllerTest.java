package com.url.shortner.demo.controller;

import com.url.shortner.demo.TestRedisConfiguration;
import com.url.shortner.demo.exception.ResourceNotFoundException;
import com.url.shortner.demo.repository.UrlRepository;
import com.url.shortner.demo.service.RedisCacheService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
        ,classes = TestRedisConfiguration.class)
class UrlControllerTest {

    private static final String TEST_URL = "https://www.example.com";
    private static final String BASE_URL = "http://localhost:%s";
    private static final String SHORTEN_ENDPOINT = "/shorten";
    private static final String REDIRECT_ENDPOINT = "/%s";

    @LocalServerPort
    private int port;

    @Autowired
    RedisCacheService redisCacheService;

    @Autowired
    UrlRepository urlRepository;

    private static RestTemplate restTemplate;


    @BeforeAll
    public static void setup() {
        restTemplate = new RestTemplate();
    }

    @Test
    public void shortenUrl_shouldReturnShortenedUrl() {
        String url = String.format(BASE_URL, port) + SHORTEN_ENDPOINT;
        ResponseEntity<String> response = restTemplate.postForEntity(url, TEST_URL, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        String shortUrl = response.getBody();
        assertNotNull(shortUrl);

    }

    @Test
    public void shortenUrl_verifyRedisHasTheValue() {
        String url = String.format(BASE_URL, port) + SHORTEN_ENDPOINT;
        ResponseEntity<String> response = restTemplate.postForEntity(url, TEST_URL, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        String shortUrl = response.getBody();
        assertNotNull(shortUrl);

        String[] splitBySlash=shortUrl.split("/");
        String param=splitBySlash[splitBySlash.length-1];

        assertNotNull(redisCacheService.get(param));

    }

    @Test
    public void shortenUrl_verifyMongoHasTheValue() {
        String url = String.format(BASE_URL, port) + SHORTEN_ENDPOINT;
        ResponseEntity<String> response = restTemplate.postForEntity(url, TEST_URL, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        String shortUrl = response.getBody();
        assertNotNull(shortUrl);

        String[] splitBySlash=shortUrl.split("/");
        String param=splitBySlash[splitBySlash.length-1];

        assertNotNull(urlRepository.findByShortUrl(param));

    }




    @Test
    public void redirectUrl_shouldRedirectToOriginalUrl() throws InterruptedException {
        String url = String.format(BASE_URL, port) + SHORTEN_ENDPOINT;
        ResponseEntity<String> response = restTemplate.postForEntity(url, TEST_URL, String.class);
        String shortUrl = response.getBody();
        String modified=shortUrl.replace(":0",":"+port);
        TimeUnit.SECONDS.sleep(1); // wait to simulate an access delay
        ResponseEntity<String> redirectResponse = restTemplate.getForEntity(modified, String.class);
        assertEquals(HttpStatus.FOUND, redirectResponse.getStatusCode());
        assertEquals(TEST_URL, redirectResponse.getHeaders().getLocation().toString());
    }


    @Test
    public void giving_wrong_url_for_redirect() throws InterruptedException {
        String url = String.format(BASE_URL, port) + SHORTEN_ENDPOINT;
        ResponseEntity<String> response = restTemplate.postForEntity(url, TEST_URL, String.class);
        String shortUrl = response.getBody();
        String modified=shortUrl.replace(":0",":"+port);
        //Add some wrong value
        modified+="wrongValue";
        System.out.println(modified);
        TimeUnit.SECONDS.sleep(1); // wait to simulate an access delay
        String finalModified = modified;
        assertThrows(HttpClientErrorException.NotFound.class,() -> restTemplate.getForEntity(finalModified, String.class));


    }
}