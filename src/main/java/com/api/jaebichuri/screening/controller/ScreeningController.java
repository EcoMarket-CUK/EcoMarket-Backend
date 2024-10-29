package com.api.jaebichuri.screening.controller;

import com.api.jaebichuri.auction.enums.AuctionStatus;
import com.api.jaebichuri.global.response.ApiResponse;
import com.api.jaebichuri.member.entity.Member;
import com.api.jaebichuri.screening.dto.ScreeningDto;
import com.api.jaebichuri.screening.dto.ScreeningListDto;
import com.api.jaebichuri.screening.service.ScreeningService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/screenings")
public class ScreeningController {

    private final ScreeningService screeningService;

    @PostMapping
    @Operation(summary = "업체 측 심사용 경매 상품 등록 API")
    public ResponseEntity<ApiResponse<String>> createAuctionScreening(@Valid @RequestPart ScreeningDto screeningDto,
                                                                      @RequestPart List<MultipartFile> images,
                                                                      @AuthenticationPrincipal Member member) throws IOException {
        return ResponseEntity.ok(ApiResponse.onSuccess(screeningService.createAuctionScreening(screeningDto, images, member)));
    }

    @GetMapping
    @Operation(summary = "업체 측 심사용 경매 상품 목록 조회 API")
    public ResponseEntity<ApiResponse<List<ScreeningListDto>>> getAllScreenings(@AuthenticationPrincipal Member member) {
        return ResponseEntity.ok(ApiResponse.onSuccess(screeningService.getAllScreenings(member)));
    }

    @GetMapping("/member-auctions")
    @Operation(summary = "특정 회원의 경매 목록 조회 API")
    public ResponseEntity<ApiResponse<List<?>>> getAuctionsByStatus(@RequestParam AuctionStatus status,
                                                                    @AuthenticationPrincipal Member member) {
        return ResponseEntity.ok(ApiResponse.onSuccess(screeningService.getAuctionsByStatus(status, member)));
    }

}
