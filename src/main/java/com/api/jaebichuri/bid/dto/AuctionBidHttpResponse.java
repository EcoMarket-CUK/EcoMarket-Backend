package com.api.jaebichuri.bid.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "경매의 현재 값들을 반환하는 DTO")
public class AuctionBidHttpResponse {

    @Schema(description = "시작가", example = "10000")
    private Long startBidPrice;
    @Schema(description = "현재 최고 입찰가", example = "30000")
    private Long topBidPrice;
    @Schema(description = "입찰 요청 가능가", example = "33000")
    private Long canBidPrice;
    @Schema(description = "경매 종료 시각", example = "2024-11-13T11:34:32.502Z")
    private LocalDateTime endTime;
    @Schema(description = "입찰자 수", example = "5")
    private Long numOfBidders;
    @Schema(description = "최근 3개의 입찰 내역")
    private List<BidDatePriceResponse> top3BidDatePriceList;
    @Schema(description = "일자별 거래량")
    private List<BidVolumeResponse> bidVolumeResponseList;
    @Schema(description = "현재 사용자 pk값")
    private Long memberId;

    @Getter
    @Builder
    @Schema(description = "일자와 입찰가격을 반환하는 DTO")
    public static class BidDatePriceResponse {
        @Schema(description = "일자", example = "11/13 18:00")
        private String bidDate;
        @Schema(description = "입찰가", example = "30000")
        private Long bidPrice;
    }

    @Getter
    @Builder
    @Schema(description = "일자와 거래량을 반환하는 DTO")
    public static class BidVolumeResponse {
        @Schema(description = "일자", example = "11/13")
        private String date;
        @Schema(description = "거래량", example = "5")
        private Long volume;
    }
}