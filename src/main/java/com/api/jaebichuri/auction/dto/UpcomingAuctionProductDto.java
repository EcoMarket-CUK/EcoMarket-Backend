package com.api.jaebichuri.auction.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UpcomingAuctionProductDto {

    private Long productId;

    private String productName;

    private String productDescription;

    private int startPrice;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private String sellerNickname;

}
