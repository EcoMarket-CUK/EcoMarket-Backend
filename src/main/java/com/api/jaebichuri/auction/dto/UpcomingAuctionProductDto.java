package com.api.jaebichuri.auction.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpcomingAuctionProductDto {

    private Long productId;

    private String productName;

    private String startTime;

    private String startPrice;

    private String imageUrl;

}