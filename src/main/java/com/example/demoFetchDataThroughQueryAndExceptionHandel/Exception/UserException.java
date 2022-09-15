package com.example.demoFetchDataThroughQueryAndExceptionHandel.Exception;


public class UserException extends RuntimeException {
    public UserException(String msg) {
        super(msg);
    }

    //ts for
    //it is the part of ResponseEntityExceptionHandler.class
   // @ExceptionHandler(HttpRequestMethodNotSupportedException.class) //@ExceptionHandler for globalize Exception declare


}
