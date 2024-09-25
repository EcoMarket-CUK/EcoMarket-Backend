package com.api.jaebichuri.auth.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AuthSuccessDto {

    private String clientId;
    private String nickname;
}