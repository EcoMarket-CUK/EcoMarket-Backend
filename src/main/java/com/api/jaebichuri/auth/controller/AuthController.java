package com.api.jaebichuri.auth.controller;

import com.api.jaebichuri.auth.dto.TokenResponseDto;
import com.api.jaebichuri.auth.service.AuthService;
import com.api.jaebichuri.global.response.ApiResponse;
import com.api.jaebichuri.member.service.MemberService;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/oauth2/kakao")
public class AuthController {

    private final AuthService authService;
    private final MemberService memberService;

    /**
     * 해당 핸들러로 카카오 소셜 로그인 요청 시 kakao 로그인 페이지로 리다이렉트
     */
    @GetMapping
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
    @GetMapping("/code")
    public ResponseEntity<ApiResponse<TokenResponseDto>> callBack(
        @RequestParam(required = false) String code)
        throws JsonProcessingException {
        Map<String, String> memberInfo = authService.getMemberInfo(code);
        return ResponseEntity.ok(
            ApiResponse.onSuccess(memberService.successSocialLogin(memberInfo)));
    }
}