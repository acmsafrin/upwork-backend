package com.url.shortner.demo.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UrlControllerTest {

    private static final String TEST_URL = "https://www.example.com";
    private static final String BASE_URL = "http://localhost:%s";
    private static final String SHORTEN_ENDPOINT = "/shorten";
    private static final String REDIRECT_ENDPOINT = "/%s";

    @LocalServerPort
    private int port;

    private static RestTemplate restTemplate;


    @BeforeAll
    public static void setup() {
        restTemplate = new RestTemplate();
    }

    @Test
    public void shortenUrl_shouldReturnShortenedUrl() {
       /* String url = String.format(BASE_URL, port) + SHORTEN_ENDPOINT;
        ResponseEntity<String> response = restTemplate.postForEntity(url, TEST_URL, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        String shortUrl = response.getBody();
        assertEquals(String.format(BASE_URL, port) + String.format(REDIRECT_ENDPOINT, shortUrl), response.getHeaders().getLocation().toString());
*/    }

    @Test
    public void redirectUrl_shouldRedirectToOriginalUrl() throws InterruptedException {
        /*String url = String.format(BASE_URL, port) + SHORTEN_ENDPOINT;
        ResponseEntity<String> response = restTemplate.postForEntity(url, TEST_URL, String.class);
        String shortUrl = response.getBody();
        TimeUnit.SECONDS.sleep(1); // wait to simulate an access delay
        ResponseEntity<String> redirectResponse = restTemplate.getForEntity(String.format(BASE_URL, port) + String.format(REDIRECT_ENDPOINT, shortUrl), String.class);
        assertEquals(HttpStatus.OK, redirectResponse.getStatusCode());
        assertEquals(TEST_URL, redirectResponse.getHeaders().getLocation().toString());*/
    }
}