package com.url.shortner.demo.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Steps:
 * 1. Generate a hash string using a secure hash function, such as SHA-512, from the long URL.
 * 2. Convert the hash string to a large integer.
 * 3. Encode the integer using a base conversion technique,
 *     such as base-62 encoding, to generate a short URL.
 */
public class UrlShortenerV2 {
     /**
     * In this implementation, we first generate a hash string from the long URL using SHA-512.
     * We then convert the hash string to a BigInteger using the new BigInteger(1, hash) constructor,
     * where the 1 parameter indicates that the hash is positive.
     * @param longUrl
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static String shorten(String longUrl) throws  NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-512");
        byte[] hash = digest.digest(longUrl.getBytes());
        BigInteger bigInt = new BigInteger(1, hash);
        String encoded = encodeBase62(bigInt);
        return encoded;
    }

    /**
     * Encode the BigInteger using a base-62 encoding technique,
     * which uses 62 different characters (0-9, A-Z, a-z) to represent the integer.
     * We convert the BigInteger to base-62 by repeatedly dividing it by 62 and appending
     * the remainder to a StringBuilder until the integer becomes zero.
     * We then reverse the StringBuilder and return the resulting string as the short URL.
     * @param bigInt
     * @return
     */
    private static String encodeBase62(BigInteger bigInt) {
        final String characters = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        StringBuilder sb = new StringBuilder();
        while (bigInt.compareTo(BigInteger.ZERO) > 0) {
            BigInteger[] divideAndRemainder = bigInt.divideAndRemainder(BigInteger.valueOf(62));
            bigInt = divideAndRemainder[0];
            int index = divideAndRemainder[1].intValue();
            sb.append(characters.charAt(index));
        }
        return sb.reverse().toString();
    }
}
