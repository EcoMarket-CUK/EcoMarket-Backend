package com.api.jaebichuri.auction.controller;

import com.api.jaebichuri.auction.dto.ProductResponseDto;
import com.api.jaebichuri.auction.dto.UpcomingAuctionProductDto;
import com.api.jaebichuri.auction.service.ProductService;
import com.api.jaebichuri.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auction")
@Tag(name = "경매 물품 API")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/products")
    @Operation(summary = "전체 경매 물품 목록 조회 API")
    public ResponseEntity<ApiResponse<List<ProductResponseDto>>> getAllAuctionProducts() {
        return ResponseEntity.ok(ApiResponse.onSuccess(productService.getAllAuctionProducts()));
    }

    @GetMapping("/{auctionId}")
    @Operation(summary = "진행 예정 경매 물품 상세 정보 조회 API")
    public ResponseEntity<ApiResponse<UpcomingAuctionProductDto>> getUpcomingAuctionProductDetails(@PathVariable Long auctionId) {
        return ResponseEntity.ok(ApiResponse.onSuccess(productService.getUpcomingAuctionProductDetails(auctionId)));
    }

}
