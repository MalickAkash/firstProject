package com.example.demoFetchDataThroughQueryAndExceptionHandel.Entity;

import org.springframework.http.HttpStatus;

public class Response {
    public HttpStatus status;
    public String message;
    public Object object;

    public Response(HttpStatus status, String message, Object object) {
        this.status = status;
        this.message = message;
        this.object = object;
    }

    public Response() {

    }

    public HttpStatus getStatus(HttpStatus ok) {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMessage(String s) {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getObject(EmployeeEntity emp) {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
