package com.api.jaebichuri.auction.dto;

import com.api.jaebichuri.auction.enums.AuctionCategory;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScreeningDto {

    private String productName;

    private String productDescription;

    private Long desiredStartPrice;

    private AuctionCategory auctionCategory;

}
