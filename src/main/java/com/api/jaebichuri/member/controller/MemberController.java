package com.api.jaebichuri.member.controller;


import com.api.jaebichuri.auth.dto.TokenResponseDto;
import com.api.jaebichuri.global.response.ApiResponse;
import com.api.jaebichuri.member.entity.Member;
import com.api.jaebichuri.member.service.MemberService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
@Tag(name = "멤버 API")
public class MemberController {

    private final MemberService memberService;

}