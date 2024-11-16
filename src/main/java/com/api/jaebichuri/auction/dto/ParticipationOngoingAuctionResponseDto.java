package com.api.jaebichuri.auction.dto;

import com.api.jaebichuri.auction.enums.AuctionCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@Schema(description = "내가 참여한 진행 중인 경매 목록 조회에 대한 정보 DTO")
public class ParticipationOngoingAuctionResponseDto {

    @Schema(description = "경매 상품 식별자 ID", example = "1")
    private Long productId;
    @Schema(description = "상품명", example = "모자")
    private String productName;
    @Schema(description = "상품 설명", example = "모자에 대한 설명")
    private String productDescription;
    @Schema(description = "최고 입찰가", example = "75000")
    private Long topBidPrice;
    @Schema(description = "나의 최고 입찰가", example = "75000 이하")
    private Long myBidPrice;
    @Schema(description = "경매 카테고리", example = "CLOTHING")
    private AuctionCategory auctionCategory;
    @Schema(description = "상품 이미지 URL", example = "https://example.com/image.jpg")
    private String imageUrl;
}