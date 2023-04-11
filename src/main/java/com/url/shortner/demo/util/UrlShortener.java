package com.url.shortner.demo.util;

public class UrlShortener {
    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int BASE = ALPHABET.length();

    public static String shorten(String longUrl) {
        int hashCode = longUrl.hashCode();
        StringBuilder sb = new StringBuilder();
        while (hashCode > 0) {
            sb.append(ALPHABET.charAt(hashCode % BASE));
            hashCode /= BASE;
        }
        return sb.toString();
    }
}
