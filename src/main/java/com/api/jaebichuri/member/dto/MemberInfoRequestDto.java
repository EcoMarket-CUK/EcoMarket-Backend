package com.api.jaebichuri.member.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class MemberInfoRequestDto {

    @NotBlank
    private String name;
    @NotBlank
    private String nickname;
    @NotBlank
    private String zipcode;
    @NotBlank
    private String address;
}