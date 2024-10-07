package com.api.jaebichuri.auction.dto;

import com.api.jaebichuri.auction.enums.AuctionCategory;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EndedAuctionDto {

    private Long endedAuctionId;

    private String productName;

    private Long finalBidPrice;

    private Boolean isBidSuccessful;

    private AuctionCategory auctionCategory;

}
