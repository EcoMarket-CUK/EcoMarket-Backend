package com.api.jaebichuri.auction.dto;

import com.api.jaebichuri.auction.enums.AuctionCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "진행 예정인 경매 목록 조회에 대한 정보 DTO")
public class UpcomingAuctionProductDto {

    @Schema(description = "경매 상품 식별자 ID", example = "1")
    private Long productId;

    @Schema(description = "상품명", example = "모자")
    private String productName;

    @Schema(description = "경매 시작 시간", example = "2024-11-01 12:00:00")
    private String startTime;

    @Schema(description = "입찰 시작가", example = "10000")
    private String startPrice;

    @Schema(description = "경매 카테고리", example = "CLOTHING")
    private AuctionCategory auctionCategory;

    @Schema(description = "상품 이미지 URL", example = "https://example.com/image.jpg")
    private String imageUrl;

}