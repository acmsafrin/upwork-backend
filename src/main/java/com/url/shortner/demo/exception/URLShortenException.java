package com.url.shortner.demo.exception;



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
