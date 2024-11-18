package com.api.jaebichuri.global.response.code.status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorStatus {

    // 인증 관련 에러
    _TOKEN_MISMATCH(HttpStatus.UNAUTHORIZED, "AUTH400", "토큰 종류가 올바르지 않습니다."),
    _TOKEN_INVALID(HttpStatus.UNAUTHORIZED, "AUTH401", "토큰이 유효하지 않습니다."),
    _TOKEN_MALFORMED(HttpStatus.UNAUTHORIZED, "AUTH402", "올바르지 않은 토큰입니다."),
    _TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "AUTH403", "토큰이 만료되었습니다."),
    _CLIENT_ID_NOT_FOUND(HttpStatus.UNAUTHORIZED, "AUTH404", "해당 토큰의 clientId의 사용자를 찾을 수 없습니다."),
    _KAKAO_LOGIN_REQUIRED(HttpStatus.UNAUTHORIZED, "AUTH405", "카카오 로그인 이후 재요청해주세요."),
    _UNAUTHORIZED_MEMBER(HttpStatus.UNAUTHORIZED, "AUTH406", "인증되지 않은 사용자입니다."),

    // 인가 관련 에러
    _ACCESS_DENIED(HttpStatus.FORBIDDEN, "AUTHZ400", "해당 경로에 접근할 수 있는 권한이 없습니다."),

    _MEMBER_NOT_FOUND(HttpStatus.BAD_REQUEST, "MEMBER400", "해당 멤버를 찾을 수 없습니다."),
    _MEMBER_TOKEN_NOT_FOUND(HttpStatus.BAD_REQUEST, "MEMBER401", "해당 멤버의 리프레시 토큰이 존재하지 않습니다."),
    _MEMBER_TOKEN_MISMATCH(HttpStatus.BAD_REQUEST, "MEMBER402", "해당 멤버의 리프레시 토큰과 전달 받은 리프레시 토큰이 동일하지 않습니다."),
    _MEMBER_ALREADY_LOGOUT(HttpStatus.BAD_REQUEST, "MEMBER403", "이미 로그아웃 상태입니다."),

    // 경매 관련 에러
    _AUCTION_NOT_FOUND(HttpStatus.BAD_REQUEST, "AUCTION400", "해당 경매를 찾을 수 없습니다."),

    // 경매 심사 관련 에러
    _IMAGE_UPLOAD_FAILED(HttpStatus.BAD_REQUEST, "SCREENING400", "이미지 업로드에 실패했습니다."),
    _MIN_IMAGES_REQUIRED(HttpStatus.BAD_REQUEST, "SCREENING401", "최소 1개 이상의 이미지를 업로드해야 합니다."),
    _MAX_IMAGES_EXCEEDED(HttpStatus.BAD_REQUEST, "SCREENING402", "최대 3개의 이미지만 업로드할 수 있습니다."),
    _SCREENING_NOT_FOUND(HttpStatus.NOT_FOUND, "SCREENING403", "해당 심사를 찾을 수 없습니다."),

    // 입찰 관련 에러
    _AUCTION_BID_GET_LOCK_FAILED(HttpStatus.BAD_REQUEST, "AUCTIONBID400", "락획득에 실패하여 입찰에 실패했습니다."),
    _AUCTION_BID_BELOW_STARTING_PRICE(HttpStatus.BAD_REQUEST, "AUCTIONBID401", "시작 입찰가보다 낮은 가격에 입찰할 수 없습니다."),
    _AUCTION_BID_BELOW_MINIMUM_INCREMENT(HttpStatus.BAD_REQUEST, "AUCTIONBID402", "현재 최고 입찰가에서 10% 높은 가격에 입찰할 수 있습니다."),
    _AUCTION_BID_ALREADY_HIGHEST(HttpStatus.BAD_REQUEST, "AUCTIONBID403", "이미 최고 입찰가로 입찰 중입니다."),

    // 배송 관련 에러
    _SHIPPING_NOT_FOUND(HttpStatus.BAD_REQUEST, "SHIPPING400", "해당 배송 정보를 찾을 수 없습니다.")
    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}