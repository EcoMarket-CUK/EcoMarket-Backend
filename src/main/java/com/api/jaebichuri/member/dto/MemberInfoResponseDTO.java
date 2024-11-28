package com.api.jaebichuri.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "사용자 추가 정보 입력 DTO")
public class MemberInfoResponseDTO {

    private String name;
    private String nickname;
    private String zipCode;
    private String address;
}