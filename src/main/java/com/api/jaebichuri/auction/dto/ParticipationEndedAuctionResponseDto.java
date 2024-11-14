package com.api.jaebichuri.auction.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@JsonPropertyOrder({"month", "isSuccessBid", "productId", "productName", "productDescription",
    "topBidPrice", "myBidPrice", "imageUrl"})
@Schema(description = "내가 참여한 종료 경매 목록 조회에 대한 정보 DTO")
public class ParticipationEndedAuctionResponseDto {

    @Schema(description = "입찰했던 달", example = "11")
    private String month;
    @Schema(description = "입찰 결과", example = "true")
    @JsonProperty("isSuccessBid")
    private Boolean isSuccessBid; // 낙찰(true) / 유찰(false)
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
    @Schema(description = "상품 이미지 URL", example = "https://example.com/image.jpg")
    private String imageUrl;
}