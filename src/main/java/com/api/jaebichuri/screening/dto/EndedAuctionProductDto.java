package com.api.jaebichuri.screening.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "마감된 경매 목록 조회에 대한 정보 DTO")
public class EndedAuctionProductDto {

    @Schema(description = "경매 상품 식별자 ID", example = "1")
    private Long productId;

    @Schema(description = "상품명", example = "모자")
    private String productName;

    @Schema(description = "상품명", example = "모자에 대한 설명입니다.")
    private String productDescription;

    @Schema(description = "최종 입찰가", example = "70000")
    private Long finalBidPrice;

    @Schema(description = "최종 입찰자 수", example = "20")
    private Long numOfBidders;

    @Schema(description = "입찰 성공 여부", example = "true")
    private Boolean isBidSuccessful;

    @Schema(description = "상품 이미지 URL", example = "https://example.com/image.jpg")
    private String imageUrl;

}
