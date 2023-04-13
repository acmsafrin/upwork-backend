package com.url.shortner.demo.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

@Slf4j
public class UrlShortener {



    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final long BASE = ALPHABET.length();


    @Value("${token.secret}")
    private static String salt;

    /**
     * There is chance for collision
     * @param longUrl
     * @return
     */
    public static String shorten(String longUrl) {
        int hashCode = (longUrl+salt).hashCode();
        final long OFFSET = Long.MAX_VALUE;
        long modifed=Math.abs(hashCode+OFFSET);
        log.info("URL"+longUrl+"Hashcode "+hashCode+"::"+modifed);
        StringBuilder sb = new StringBuilder();
        while (modifed > 0) {
            int index=(int)(modifed % BASE);
            sb.append(ALPHABET.charAt(index));
            modifed /= BASE;
        }
        log.info("URL"+longUrl+"Final "+sb);
        return sb.toString();
    }
}
