package com.sparta.lv5.wishes.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public class MessageDto {
    private final String message;
    private final HttpStatus status;
}
