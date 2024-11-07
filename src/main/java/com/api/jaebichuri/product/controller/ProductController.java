package com.api.jaebichuri.product.controller;

import com.api.jaebichuri.product.dto.UpcomingProductDetailsDto;
import com.api.jaebichuri.product.service.ProductService;
import com.api.jaebichuri.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
@Tag(name = "Product", description = "경매 상품 상세 정보 조회 관련 API")
public class ProductController {

    private final ProductService productService;

    @Operation(
            summary = "진행 예정 경매 상품 상세 정보 조회 API",
            description = "진행 예정 경매에 대한 상세 정보를 반환하는 API입니다.",
            parameters = {
                    @Parameter(name = "auctionId", description = "진행 예정 경매 식별자 ID", required = true)
            },
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "COMMON200",
                            description = "진행 예정 경매 물품 상세 정보 조회 결과",
                            content = @Content(schema = @Schema(implementation = UpcomingProductDetailsDto.class))
                    )
            }
    )
    @GetMapping("/upcoming/{auctionId}")
    public ResponseEntity<ApiResponse<UpcomingProductDetailsDto>> getUpcomingAuctionProductDetails(@PathVariable Long auctionId) {
        return ResponseEntity.ok(ApiResponse.onSuccess(productService.getUpcomingAuctionProductDetails(auctionId)));
    }

}

