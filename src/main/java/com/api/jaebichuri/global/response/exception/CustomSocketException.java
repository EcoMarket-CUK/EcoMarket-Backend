package com.api.jaebichuri.global.response.exception;

import com.api.jaebichuri.global.response.code.status.ErrorStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomSocketException extends RuntimeException {

    private final ErrorStatus errorStatus;
    private final Long memberId;
}