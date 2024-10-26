package com.api.jaebichuri.screening.dto;

import com.api.jaebichuri.auction.enums.AuctionCategory;
import com.api.jaebichuri.screening.enums.AuctionScreeningStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScreeningListDto {

    private Long screeningId;

    private String productName;

    private String productDescription;

    private Long desiredStartPrice;

    private AuctionCategory auctionCategory;

    private AuctionScreeningStatus screeningStatus;

    private String imageUrl;

}
