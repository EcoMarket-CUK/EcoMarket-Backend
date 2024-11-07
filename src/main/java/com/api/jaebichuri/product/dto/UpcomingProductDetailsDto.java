package com.api.jaebichuri.product.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "진행 예정인 경매 물품 상세 조회에 대한 정보 DTO")
public class UpcomingProductDetailsDto {

    @Schema(description = "경매 상품 식별자 ID", example = "1")
    private Long productId;

    @Schema(description = "상품명", example = "모자")
    private String productName;

    @Schema(description = "상품명", example = "모자에 대한 설명입니다.")
    private String productDescription;

    @Schema(description = "입찰 시작가", example = "10000")
    private String startPrice;

    @Schema(description = "경매 시작 시간", example = "2024-11-01 12:00:00")
    private String startTime;

    @Schema(description = "경매 종료 시간", example = "2024-11-01 17:00:00")
    private String endTime;

    @Schema(description = "경매 등록자 닉네임", example = "에코마켓")
    private String sellerNickname;

}
