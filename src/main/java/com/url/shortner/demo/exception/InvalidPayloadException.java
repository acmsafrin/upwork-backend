package com.url.shortner.demo.exception;


public class InvalidPayloadException extends Exception {

	private static final long serialVersionUID = 1L;
	
	/**
	 * custom constructor.
	 * @param errorMessage
	 */
	public InvalidPayloadException(String errorMessage) {
		super(errorMessage);
	}

}
