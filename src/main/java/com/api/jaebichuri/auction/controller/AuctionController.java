package com.api.jaebichuri.auction.controller;

import com.api.jaebichuri.auction.dto.OngoingAuctionProductDto;
import com.api.jaebichuri.auction.dto.UpcomingAuctionProductDto;
import com.api.jaebichuri.auction.enums.AuctionCategory;
import com.api.jaebichuri.auction.enums.AuctionStatus;
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
@RequestMapping("/auctions")
public class AuctionController {

    private final AuctionService auctionService;

    @GetMapping
    @Operation(summary = "경매 TOP5 목록 조회 API")
    public ResponseEntity<ApiResponse<List<?>>> getTopAuctionsByStatus(@RequestParam AuctionStatus status,
                                                               @RequestParam(required = false) AuctionCategory category) {
        return ResponseEntity.ok(ApiResponse.onSuccess(auctionService.getTopAuctionsByStatus(status, category)));
    }

    @GetMapping("/ongoing")
    @Operation(summary = "진행 중인 경매 전체 목록 조회 API")
    public ResponseEntity<ApiResponse<List<OngoingAuctionProductDto>>> getAllOngoingAuctions(@RequestParam(required = false) AuctionCategory category) {
        return ResponseEntity.ok(ApiResponse.onSuccess(auctionService.getAllOngoingAuctions(category)));
    }

}
