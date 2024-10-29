package com.api.jaebichuri.auction.dto;

import com.api.jaebichuri.auction.enums.AuctionCategory;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OngoingAuctionProductDto {

    private Long productId;

    private String productName;

    private String productDescription;

    private Long currentPrice;

    private Long currentBidders;

    private AuctionCategory auctionCategory;

    private String imageUrl;

}
