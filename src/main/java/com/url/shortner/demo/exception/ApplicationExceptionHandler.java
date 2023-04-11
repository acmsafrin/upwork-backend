package com.url.shortner.demo.exception;

import com.url.shortner.demo.dto.ErrorResponseModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@ControllerAdvice
public class ApplicationExceptionHandler {




    /**
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(value = {ResourceNotFoundException.class})
    public ResponseEntity<Object> handleResourceNotFoundException (ResourceNotFoundException ex, WebRequest request) {
        String errDesc = ex.getMessage();
        ErrorResponseModel errorMessage  = new ErrorResponseModel(new Date(), errDesc);
        ex.printStackTrace();
        return new ResponseEntity<Object>(
                errorMessage, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidPayloadException.class)
    public ResponseEntity<?> handleValidationExceptions(InvalidPayloadException ex) {
        String errDesc = ex.getMessage();
        ErrorResponseModel errorMessage  = new ErrorResponseModel(new Date(), errDesc);
        ex.printStackTrace();
        return new ResponseEntity<Object>(
                errorMessage, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {URLShortenException.class})
    public ResponseEntity<Object> handleAnyException (URLShortenException ex, WebRequest request) {
        String errDesc = ex.getMessage();
        ErrorResponseModel errorMessage  = new ErrorResponseModel(new Date(), errDesc);
        ex.printStackTrace();
        return new ResponseEntity<Object>(
                errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


    /**
     * This specific method handles all exceptions.
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handleAnyException (Exception ex, WebRequest request) {

        String errDesc =  ex.getMessage();
        ErrorResponseModel errorMessage  = new ErrorResponseModel(new Date(), errDesc);

        return new ResponseEntity<Object>(
                errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
