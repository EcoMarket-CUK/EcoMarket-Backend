package com.api.jaebichuri.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
@Schema(description = "사용자 추가 정보 입력 DTO")
public class MemberInfoRequestDto {

    @NotBlank
    @Schema(description = "이름", example = "김땡땡")
    private String name;
    @NotBlank
    @Schema(description = "닉네임", example = "땡땡이")
    private String nickname;
    @NotBlank
    @Schema(description = "우편번호", example = "12345")
    private String zipcode;
    @NotBlank
    @Schema(description = "주소", example = "경기도 부천시 원미구 지봉로 43")
    private String address;
}