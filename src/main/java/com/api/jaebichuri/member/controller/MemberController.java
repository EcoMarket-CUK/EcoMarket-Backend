package com.api.jaebichuri.member.controller;

import com.api.jaebichuri.global.response.ApiResponse;
import com.api.jaebichuri.member.dto.MemberInfoRequestDto;
import com.api.jaebichuri.member.dto.MemberInfoResponseDTO;
import com.api.jaebichuri.member.entity.Member;
import com.api.jaebichuri.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
@Tag(name = "Member", description = "사용자 관련 API")
public class MemberController {

    private final MemberService memberService;

    @Operation(
        summary = "사용자 추가 정보 입력 or 수정 API",
        description = "로그인 이후 사용자의 추가 정보를 입력 받는 API입니다. 해당 API는 사용자 인증이 요구됩니다.",
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "COMMON200",
                description = "사용자 정보 저장 결과를 반환",
                content = @Content(schema = @Schema(implementation = String.class))
            )
        }
    )
    @PutMapping("/info")
    public ResponseEntity<ApiResponse<String>> saveMemberInfo(
        @AuthenticationPrincipal Member member,
        @Parameter(name = "memberInfoRequestDto", description = "사용자 추가 정보 입력 DTO", required = true)
        @Valid @RequestBody MemberInfoRequestDto memberInfoRequestDto) {
        memberService.saveMemberInfo(member, memberInfoRequestDto);
        return ResponseEntity.ok(ApiResponse.onSuccess("사용자 정보 저장 완료"));
    }

    @Operation(
        summary = "사용자 정보 조회 API (마이페이지)",
        description = "사용자 정보 조회 API입니다. 해당 API는 사용자 인증이 요구됩니다.",
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "COMMON200",
                description = "사용자 정보를 반환",
                content = @Content(schema = @Schema(implementation = MemberInfoResponseDTO.class))
            )
        }
    )
    @GetMapping("/info")
    public ResponseEntity<ApiResponse<MemberInfoResponseDTO>> getMemberInfo(
        @AuthenticationPrincipal Member member) {
        return ResponseEntity.ok(ApiResponse.onSuccess(memberService.getMemberInfo(member)));
    }

    @PostMapping("/withdraw")
    public ResponseEntity<ApiResponse<String>> withdraw(@AuthenticationPrincipal Member member) {
        memberService.withdraw(member);
        return ResponseEntity.ok(ApiResponse.onSuccess("회원 탈퇴 완료"));
    }

}