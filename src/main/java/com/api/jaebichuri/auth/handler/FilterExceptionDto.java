package com.api.jaebichuri.auth.handler;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FilterExceptionDto {

    private String message;
}