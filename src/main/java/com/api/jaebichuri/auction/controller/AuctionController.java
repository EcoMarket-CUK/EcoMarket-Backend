package com.api.jaebichuri.auction.controller;

import com.api.jaebichuri.auction.dto.ProductDto;
import com.api.jaebichuri.auction.enums.AuctionCategory;
import com.api.jaebichuri.auction.service.AuctionService;
import com.api.jaebichuri.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auction")
public class AuctionController {

    private final AuctionService auctionService;

    @GetMapping("/ongoing")
    @Operation(summary = "진행 중인 경매 목록 조회 API")
    public ResponseEntity<ApiResponse<List<ProductDto>>> getOngoingAuctions(@RequestParam(required = false) AuctionCategory category) {
        return ResponseEntity.ok(ApiResponse.onSuccess(auctionService.getOngoingAuctions(category)));
    }

    @GetMapping("/upcoming")
    @Operation(summary = "진행 예정인 경매 목록 조회 API")
    public ResponseEntity<ApiResponse<List<ProductDto>>> getUpcomingAuctions(@RequestParam(required = false)AuctionCategory category) {
        return ResponseEntity.ok(ApiResponse.onSuccess(auctionService.getUpcomingAuctions(category)));
    }

}
