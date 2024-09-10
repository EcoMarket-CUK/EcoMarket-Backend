package com.api.jaebichuri.global.response.exception;

import com.api.jaebichuri.global.response.ApiResponse;
import com.api.jaebichuri.global.response.code.status.ErrorStatus;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionControllerAdvice {

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
}