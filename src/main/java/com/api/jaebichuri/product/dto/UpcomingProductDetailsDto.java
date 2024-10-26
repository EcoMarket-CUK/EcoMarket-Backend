package com.api.jaebichuri.auction.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpcomingAuctionProductDto {

    private Long productId;

    private String productName;

    private String productDescription;

    private Long startPrice;

    private String startTime;

    private String endTime;

    private String sellerNickname;

}
