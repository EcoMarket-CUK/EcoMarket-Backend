package com.api.jaebichuri.product.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpcomingProductDetailsDto {

    private Long productId;

    private String productName;

    private String productDescription;

    private Long startPrice;

    private String startTime;

    private String endTime;

    private String sellerNickname;

}
