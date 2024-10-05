package com.api.jaebichuri.auction.controller;

import com.api.jaebichuri.auction.dto.UpcomingAuctionProductDto;
import com.api.jaebichuri.auction.service.ProductService;
import com.api.jaebichuri.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/auction/products")
@Tag(name = "경매 물품 API")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/upcoming/{auctionId}")
    @Operation(summary = "진행 예정 경매 물품 상세 정보 조회 API")
    public ResponseEntity<ApiResponse<UpcomingAuctionProductDto>> getUpcomingAuctionProductDetails(@PathVariable Long auctionId) {
        return ResponseEntity.ok(ApiResponse.onSuccess(productService.getUpcomingAuctionProductDetails(auctionId)));
    }

}
