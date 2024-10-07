package com.api.jaebichuri.auction.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDto {

    private Long productId;

    private String productName;

    private String productDescription;

    private String startTime;

    private String endTime;

}
