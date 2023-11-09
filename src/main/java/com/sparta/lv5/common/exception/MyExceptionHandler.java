package com.sparta.lv5.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;

@RestControllerAdvice
public class MyExceptionHandler {
    // InsufficientAuthenticationException (AccessDeniedException)
    @ExceptionHandler({BadCredentialsException.class, UsernameNotFoundException.class, AccessDeniedException.class, AuthenticationException.class})
    public ResponseEntity<ErrorMessage> authenticationException(RuntimeException ex) {
        String message = findExceptionCase(ex);
        ErrorMessage errorMessage = new ErrorMessage(message, HttpStatus.UNAUTHORIZED);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .header("Location", String.valueOf(URI.create("/api/user/login")))
                .body(errorMessage);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> badRequestException(MethodArgumentNotValidException ex) {
        ErrorMessage errorMessage = new ErrorMessage(ex.getBindingResult()
                .getAllErrors()
                .get(0)
                .getDefaultMessage(),
                HttpStatus.BAD_REQUEST);
        return ResponseEntity.badRequest().body(errorMessage);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorMessage> illegalArgumentException(IllegalArgumentException ex) {
        ErrorMessage errorMessage = new ErrorMessage(ex.getMessage(), HttpStatus.BAD_REQUEST);
        return ResponseEntity.badRequest().body(errorMessage);
    }

    @ExceptionHandler(AuthorityViolationException.class)
    public ResponseEntity<ErrorMessage> authorityViolationException(AuthorityViolationException ex) {
        ErrorMessage errorMessage = new ErrorMessage(ex.getMessage(), HttpStatus.FORBIDDEN);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorMessage);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ErrorMessage> nullPointerException(NullPointerException ex) {
        ErrorMessage errorMessage = new ErrorMessage(ex.getMessage(), HttpStatus.NOT_FOUND);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }

    private String findExceptionCase(RuntimeException ex) {
        String message;
        if (ex instanceof BadCredentialsException) {
            message = "비밀번호가 틀렸습니다.";
        } else if (ex instanceof UsernameNotFoundException) {
            message = "존재하지 않는 계정입니다. 회원가입 후 로그인해주세요";
        } else {
            message = "사용자 인증에 실패했습니다.";
        }
        return message;
    }
}
