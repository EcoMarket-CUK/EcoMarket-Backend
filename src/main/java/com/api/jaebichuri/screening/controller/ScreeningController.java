package com.api.jaebichuri.auction.controller;

import com.api.jaebichuri.auction.dto.EndedAuctionDto;
import com.api.jaebichuri.auction.dto.ScreeningDto;
import com.api.jaebichuri.auction.dto.ScreeningListDto;
import com.api.jaebichuri.auction.service.ScreeningService;
import com.api.jaebichuri.global.response.ApiResponse;
import com.api.jaebichuri.member.entity.Member;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auction/screenings")
public class ScreeningController {

    private final ScreeningService screeningService;

    @PostMapping
    @Operation(summary = "업체 측 심사용 경매 상품 등록 API")
    public ResponseEntity<ApiResponse<String>> createAuctionScreening(@Valid @RequestBody ScreeningDto screeningDto, @AuthenticationPrincipal Member member) {
        return ResponseEntity.ok(ApiResponse.onSuccess(screeningService.createAuctionScreening(screeningDto, member)));
    }

    @GetMapping
    @Operation(summary = "업체 측 심사용 경매 상품 목록 조회 API")
    public ResponseEntity<ApiResponse<List<ScreeningListDto>>> getAuctionScreenings(@AuthenticationPrincipal Member member) {
        return ResponseEntity.ok(ApiResponse.onSuccess(screeningService.getAuctionScreenings(member)));
    }

    @GetMapping("/ended-auctions")
    @Operation(summary = "마감된 경매 목록 조회 API")
    public ResponseEntity<ApiResponse<List<EndedAuctionDto>>> getEndedAuctions(@AuthenticationPrincipal Member member) {
        return ResponseEntity.ok(ApiResponse.onSuccess(screeningService.getEndedAuctions(member)));
    }

}
