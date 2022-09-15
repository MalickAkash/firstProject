package com.example.demoFetchDataThroughQueryAndExceptionHandel.Exception;

import org.springframework.http.HttpHeaders;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.web.context.request.WebRequest;

import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

//not solved this

//use all global Exceptions declearation

public class GlobalException extends ResponseEntityExceptionHandler {
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
                                                                          HttpHeaders headers, HttpStatus status,
                                                                         WebRequest request) {
        //To auto generate method stub
        return new ResponseEntity<Object> ("please change the http method type",HttpStatus.NOT_FOUND);
    }
}
