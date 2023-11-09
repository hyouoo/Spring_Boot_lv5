package com.sparta.lv5.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ErrorMessage {
    private final String message;
    private final String status;

    public ErrorMessage(String message, HttpStatus status) {
        this.message = message;
        this.status = status.getReasonPhrase();
    }
}

