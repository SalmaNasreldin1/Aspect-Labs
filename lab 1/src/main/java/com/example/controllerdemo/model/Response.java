package com.example.controllerdemo.model;

import java.time.LocalDateTime;

public class Response {
    private String message;
    private Object data;
    private LocalDateTime timestamp;

    public Response() {}

    public Response(String message, Object data, LocalDateTime timestamp) {
        this.message = message;
        this.data = data;
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}