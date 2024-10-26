package com.api.jaebichuri.screening.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EndedAuctionProductDto {

    private Long productId;

    private String productName;

    private String productDescription;

    private Long finalBidPrice;

    private Long finalBidders;

    private Boolean isBidSuccessful;

    private String imageUrl;

}
