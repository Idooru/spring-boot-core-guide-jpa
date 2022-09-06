package com.example.springboot.jpa.data.dto;

import org.springframework.http.HttpStatus;

public class JsonDto<T> {
    public boolean success;
    public String message;
    public HttpStatus status;
    public T result;

    public JsonDto(boolean success, String message, HttpStatus status, T result) {
        this.success = success;
        this.message = message;
        this.status = status;
        this.result = result;
    }

}
