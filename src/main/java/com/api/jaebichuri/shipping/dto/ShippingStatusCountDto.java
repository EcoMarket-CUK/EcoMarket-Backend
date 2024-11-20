package com.api.jaebichuri.shipping.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "배송 상태별 물품 개수 조회에 대한 정보 DTO")
public class ShippingStatusCountDto {

    @Schema(description = "송금 확인 상태의 물품 개수", example = "1")
    private int paymentConfirmedCount;

    @Schema(description = "배송 준비중 상태의 물품 개수", example = "1")
    private int shippingPreparingCount;

    @Schema(description = "배송중 상태의 물품 개수", example = "1")
    private int shippingCount;

    @Schema(description = "배송 완료 상태의 물품 개수", example = "1")
    private int deliveredCount;

}

