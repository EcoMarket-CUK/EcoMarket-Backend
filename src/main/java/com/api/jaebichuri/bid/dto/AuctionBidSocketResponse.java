package com.api.jaebichuri.bid.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AuctionBidSocketResponse {

    private Long topBidPrice;
    private Long canBidPrice;
    private Long previousBidderId;
    private Long newBidderId;
    private Long auctionId;
    private Integer numOfBidders;

    @Getter
    @Builder
    public static class AuctionBidSubscribersResponse {

        private Long topBidPrice;
        private Long canBidPrice;
        private Integer numOfBidders;
    }

    @Getter
    @Builder
    @JsonPropertyOrder({"isSuccess", "code", "message"})
    public static class AuctionBidderResponse {

        @JsonProperty("isSuccess")
        private Boolean isSuccess;
        private String code;
        private String message;
    }
}
