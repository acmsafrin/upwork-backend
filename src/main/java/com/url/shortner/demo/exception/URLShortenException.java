package com.url.shortner.demo.exception;


/**
 * Use to throw custom exceptions.
 *
 * @author Shamalka Nilaweera
 * @version 1.0
 * @created 29-11-2022
 */
public class URLShortenException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * custom constructor.
     * @param errorMessage
     */
    public URLShortenException(String errorMessage) {
            super(errorMessage);
    }
}
