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
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
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

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "인증 인가 API")
public class AuthController {

    private final AuthService authService;
    private final MemberService memberService;

    /**
     * 해당 핸들러로 카카오 소셜 로그인 요청 시 kakao 로그인 페이지 url 반환
     */
    @GetMapping("/oauth2/kakao")
    @Operation(summary = "카카오 로그인 페이지로 리다이렉트 API")
    public void getLoginUrl(HttpServletResponse response) throws IOException {
        String redirectUrl = authService.getRedirectUrl();
        log.info("{}", redirectUrl);
        response.sendRedirect(redirectUrl);
    }

    @GetMapping("/oauth2/kakao/code")
    @Operation(summary = "인가 코드 받아 처리하는 콜백 API")
    public void callBack(
        @RequestParam(required = false) String code, HttpServletResponse response)
        throws IOException {
        Map<String, String> memberInfo = authService.getMemberInfo(code);
        LoginSuccessDto loginSuccessDto = memberService.login(memberInfo);

        String redirectUrl =
            "http://localhost:5173?isFirstLogin=" + loginSuccessDto.getIsFirstLogin()
                + "&accessToken=" + loginSuccessDto.getTokenResponseDto().getAccessToken() +
                "&refreshToken=" + loginSuccessDto.getTokenResponseDto().getRefreshToken();

        response.sendRedirect(redirectUrl);
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