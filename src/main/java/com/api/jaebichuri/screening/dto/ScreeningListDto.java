package com.api.jaebichuri.auction.dto;

import com.api.jaebichuri.auction.enums.AuctionCategory;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScreeningListDto {

    private Long screeningId;

    private String productName;

    private AuctionCategory auctionCategory;

}
