package com.api.jaebichuri.bid.dto;

import com.api.jaebichuri.bid.dto.AuctionBidHttpResponse.BidDatePriceResponse;
import com.api.jaebichuri.bid.dto.AuctionBidHttpResponse.BidVolumeResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.List;
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
    private List<BidDatePriceResponse> top3BidDatePriceList;
    private List<BidVolumeResponse> bidVolumeResponseList;

    @Getter
    @Builder
    public static class AuctionBidSubscribersResponse {

        private Long topBidPrice;
        private Long canBidPrice;
        private Integer numOfBidders;
        private List<BidDatePriceResponse> top3BidDatePriceList;
        private List<BidVolumeResponse> bidVolumeResponseList;
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
