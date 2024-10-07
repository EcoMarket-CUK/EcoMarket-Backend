package com.api.jaebichuri.auth.controller;

import com.api.jaebichuri.auth.dto.LoginSuccessDto;
import com.api.jaebichuri.auth.dto.TokenResponseDto;
import com.api.jaebichuri.auth.service.AuthService;
import com.api.jaebichuri.global.response.ApiResponse;
import com.api.jaebichuri.member.entity.Member;
import com.api.jaebichuri.member.service.MemberService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "인증 인가 API")
public class AuthController {

    private final AuthService authService;
    private final MemberService memberService;

    /**
     * 해당 핸들러로 카카오 소셜 로그인 요청 시 kakao 로그인 페이지로 리다이렉트
     */
    @GetMapping("/oauth2/kakao")
    @Operation(summary = "카카오 소셜 로그인 페이지 리다이렉트 API")
    public RedirectView getLoginPage() {
        String redirectUrl = authService.getRedirectUrl();
        log.info("{}", redirectUrl);
        return new RedirectView(redirectUrl);
    }

    /**
     * 1. /oauth2/kakao/url 로 요청하면 리다이렉트 주소를 반환해주고
     * 2. 해당 주소로 가서 로그인하면
     * 3. 카카오 측에서 여기 핸들러로 리다이렉트해준다.
     */
    @GetMapping("/oauth2/kakao/code")
    @Operation(summary = "로그인 API")
    public ResponseEntity<ApiResponse<LoginSuccessDto>> callBack(
        @RequestParam(required = false) String code)
        throws JsonProcessingException {
        Map<String, String> memberInfo = authService.getMemberInfo(code);
        return ResponseEntity.ok(
            ApiResponse.onSuccess(memberService.login(memberInfo)));
    }

    @GetMapping("/reissue")
    @Operation(summary = "access token 재발급 API")
    public ResponseEntity<ApiResponse<TokenResponseDto>> reissue(
        @RequestAttribute("refresh") String refreshToken) {
        return ResponseEntity.ok(ApiResponse.onSuccess(authService.reissue(refreshToken)));
    }

    @PostMapping("/logout")
    @Operation(summary = "로그아웃 API")
    public ResponseEntity<ApiResponse<String>> logout(@AuthenticationPrincipal Member member) {
        authService.deleteRefreshToken(member);
        return ResponseEntity.ok(ApiResponse.onSuccess("로그아웃 성공"));
    }
}