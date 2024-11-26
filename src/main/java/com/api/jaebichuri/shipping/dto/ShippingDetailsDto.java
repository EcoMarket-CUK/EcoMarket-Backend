package com.api.jaebichuri.shipping.dto;

import com.api.jaebichuri.auction.enums.AuctionCategory;
import com.api.jaebichuri.shipping.enums.ShippingStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "배송 중인 물품 조회에 대한 정보 DTO")
public class ShippingDetailsDto {

    @Schema(description = "배송 식별자 ID", example = "1")
    private Long shippingId;

    @Schema(description = "상품명", example = "모자")
    private String productName;

    @Schema(description = "상품명", example = "모자에 대한 설명입니다.")
    private String productDescription;

    @Schema(description = "경매 카테고리", example = "CLOTHING")
    private AuctionCategory auctionCategory;

    @Schema(description = "배송 상태", example = "SHIPPING")
    private ShippingStatus shippingStatus;

    @Schema(description = "택배사", example = "우체국 택배")
    private String shippingCompany;

    @Schema(description = "송장 번호", example = "12356515")
    private String trackingNumber;

    @Schema(description = "최종 낙찰가", example = "50000")
    private Long finalBidPrice;

    @Schema(description = "상품 이미지 URL", example = "https://example.com/image.jpg")
    private String imageUrl;

}

