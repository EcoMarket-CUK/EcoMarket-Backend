package com.api.jaebichuri.screening.dto;

import com.api.jaebichuri.auction.enums.AuctionCategory;
import com.api.jaebichuri.screening.enums.AuctionScreeningStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "심사 중인 경매 목록 조회에 대한 정보 DTO")
public class ScreeningListDto {

    @Schema(description = "심사 중인 경매 식별자 ID", example = "1")
    private Long screeningId;

    @Schema(description = "상품명", example = "모자")
    private String productName;

    @Schema(description = "상품명", example = "모자에 대한 설명입니다.")
    private String productDescription;

    @Schema(description = "희망 입찰 시작가", example = "20000")
    private Long desiredStartPrice;

    @Schema(description = "경매 카테고리", example = "CLOTHING")
    private AuctionCategory auctionCategory;

    @Schema(description = "심사 상태", example = "PRICE_REVIEW")
    private AuctionScreeningStatus screeningStatus;

    @Schema(description = "상품 이미지 URL", example = "https://example.com/image.jpg")
    private String imageUrl;

}
