package com.api.jaebichuri.bid.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AuctionBidHttpResponse {

    private Long startBidPrice;
    private Long topBidPrice;
    private Long canBidPrice;
    private LocalDateTime endTime;
    private Long numOfBidders;
}