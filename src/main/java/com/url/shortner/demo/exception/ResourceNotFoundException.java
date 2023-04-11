package com.url.shortner.demo.exception;



public class ResourceNotFoundException extends Exception{

    private static final long serialVersionUID = 1L;

    /**
     * custom constructor.
     * @param errorMessage
     */
    public  ResourceNotFoundException (String errorMessage) {
        super(errorMessage);
    }
}
