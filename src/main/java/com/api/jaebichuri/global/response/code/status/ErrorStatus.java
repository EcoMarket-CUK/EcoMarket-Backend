package com.api.jaebichuri.global.response.code.status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorStatus {

    // 인증 관련 에러
    _TOKEN_INVALID(HttpStatus.UNAUTHORIZED, "AUTH401", "토큰이 유효하지 않습니다."),
    _TOKEN_MALFORMED(HttpStatus.UNAUTHORIZED, "AUTH402", "올바르지 않은 토큰입니다."),
    _TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "AUTH403", "토큰이 만료되었습니다."),
    _CLIENT_ID_NOT_FOUND(HttpStatus.UNAUTHORIZED, "AUTH404", "해당 토큰의 clientId의 사용자를 찾을 수 없습니다."),
    _KAKAO_LOGIN_REQUIRED(HttpStatus.UNAUTHORIZED, "AUTH405", "카카오 로그인 이후 재요청해주세요."),
    _UNAUTHORIZED_MEMBER(HttpStatus.UNAUTHORIZED, "AUTH406", "인증되지 않은 사용자입니다."),

    // 인가 관련 에러
    _ACCESS_DENIED(HttpStatus.FORBIDDEN, "AUTHZ401", "해당 경로에 접근할 수 있는 권한이 없습니다.")

    ;


    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}