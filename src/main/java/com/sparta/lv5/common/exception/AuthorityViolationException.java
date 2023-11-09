package com.sparta.lv5.common.exception;

public class AuthorityViolationException extends RuntimeException {
    public AuthorityViolationException(String msg) {
        super(msg);
    }
}
