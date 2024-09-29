package com.api.jaebichuri.auction.dto;

import com.api.jaebichuri.auction.enums.AuctionStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ProductResponseDto {

    private Long productId;

    private String productName;

    private int startPrice;

    private LocalDateTime startTime;

    private String sellerNickname;

    private AuctionStatus auctionStatus;

}
