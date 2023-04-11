package com.url.shortner.demo.exception;

/**
 * @author Shamalka Nilaweera
 * @version 1.0
 * @created 29-11-2022
 */

public class ResourceNotFoundException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    /**
     * custom constructor.
     * @param errorMessage
     */
    public  ResourceNotFoundException (String errorMessage) {
        super(errorMessage);
    }
}
