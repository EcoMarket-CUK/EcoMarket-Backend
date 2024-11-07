package com.api.jaebichuri.bid.dto;

import java.time.LocalDateTime;
import java.util.List;
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
    private List<BidDatePriceResponse> top3BidDatePriceList;
    private List<BidVolumeResponse> bidVolumeResponseList;

    @Getter
    @Builder
    public static class BidDatePriceResponse {
        private String bidDate;
        private Long bidPrice;
    }

    @Getter
    @Builder
    public static class BidVolumeResponse {
        private String date;
        private Long volume;
    }
}