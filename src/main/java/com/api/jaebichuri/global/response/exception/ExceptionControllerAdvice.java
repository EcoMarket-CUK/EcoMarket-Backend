package com.api.jaebichuri.global.response.exception;

import com.api.jaebichuri.bid.dto.AuctionBidSocketResponse.AuctionBidderResponse;
import com.api.jaebichuri.bid.mapper.AuctionBidMapper;
import com.api.jaebichuri.global.response.ApiResponse;
import com.api.jaebichuri.global.response.code.status.ErrorStatus;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionControllerAdvice {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final AuctionBidMapper auctionBidMapper;

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<ApiResponse<Object>> handleSignatureException(SignatureException e) {
        log.info("RestControllerAdvice 동작");
        ErrorStatus errorStatus = ErrorStatus._TOKEN_INVALID;

        return ResponseEntity.status(errorStatus.getHttpStatus()).body(ApiResponse.onFailure(
            errorStatus.getCode(), errorStatus.getMessage(), e.getMessage()));
    }

    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<ApiResponse<Object>> handleJwtException(MalformedJwtException e) {
        log.info("RestControllerAdvice 동작");
        ErrorStatus errorStatus = ErrorStatus._TOKEN_MALFORMED;

        return ResponseEntity.status(errorStatus.getHttpStatus()).body(ApiResponse.onFailure(
            errorStatus.getCode(), errorStatus.getMessage(), e.getMessage()));
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ApiResponse<Object>> handleExpiredJwtException(ExpiredJwtException e) {
        log.info("RestControllerAdvice 동작");
        ErrorStatus errorStatus = ErrorStatus._TOKEN_EXPIRED;

        return ResponseEntity.status(errorStatus.getHttpStatus()).body(ApiResponse.onFailure(
            errorStatus.getCode(), errorStatus.getMessage(), e.getMessage()));
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleUsernameNotFoundException(
        UsernameNotFoundException e) {
        log.info("RestControllerAdvice 동작");
        ErrorStatus errorStatus = ErrorStatus._KAKAO_LOGIN_REQUIRED;

        return ResponseEntity.status(errorStatus.getHttpStatus()).body(ApiResponse.onFailure(
            errorStatus.getCode(), errorStatus.getMessage(), e.getMessage()));
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiResponse<Object>> handleAuthenticationException(
        AuthenticationException e) {
        log.info("RestControllerAdvice 동작");
        ErrorStatus errorStatus = ErrorStatus._UNAUTHORIZED_MEMBER;

        return ResponseEntity.status(errorStatus.getHttpStatus())
            .body(ApiResponse.onFailure(errorStatus.getCode(),
                errorStatus.getMessage(), e.getMessage()));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<Object>> handleAccessDeniedException(
        AccessDeniedException e) {
        log.info("RestControllerAdvice 동작");
        ErrorStatus errorStatus = ErrorStatus._ACCESS_DENIED;

        return ResponseEntity.status(errorStatus.getHttpStatus()).body(ApiResponse.onFailure(
            errorStatus.getCode(), errorStatus.getMessage(), e.getMessage()));
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiResponse<Object>> handleCustomException(CustomException e) {
        ErrorStatus errorStatus = e.getErrorStatus();

        return ResponseEntity.status(errorStatus.getHttpStatus()).body(ApiResponse.onFailure(
            errorStatus.getCode(), errorStatus.getMessage(), null));
    }

    @ExceptionHandler(ServletRequestBindingException.class)
    public ResponseEntity<ApiResponse<Object>> handleServletRequestBindingException(ServletRequestBindingException e) {
        ErrorStatus errorStatus = ErrorStatus._TOKEN_MISMATCH;
        return ResponseEntity.status(errorStatus.getHttpStatus()).body(ApiResponse.onFailure(
            errorStatus.getCode(), errorStatus.getMessage(), e.getMessage()));
    }

    @ExceptionHandler(MissingServletRequestPartException.class)
    public ResponseEntity<ApiResponse<Object>> handleMissingServletRequestPartException(MissingServletRequestPartException e) {
        ErrorStatus errorStatus = ErrorStatus._IMAGE_REQUIRED;
        return ResponseEntity.status(errorStatus.getHttpStatus()).body(ApiResponse.onFailure(
            errorStatus.getCode(), errorStatus.getMessage(), e.getMessage()));
    }

    /**
     * 웹 소켓 예외 처리
     */
    @MessageExceptionHandler(CustomSocketException.class)
    public void handleCustomSocketException(CustomSocketException e) {
        String message = e.getErrorStatus().getMessage();
        AuctionBidderResponse auctionBidderResponse = auctionBidMapper.toFailResponse(
            e.getErrorStatus(), message);

        simpMessagingTemplate.convertAndSend("/sub/members/" + e.getMemberId(),
            auctionBidderResponse);
    }
}
