package com.api.jaebichuri.product.dto;

import static com.api.jaebichuri.bid.dto.AuctionBidHttpResponse.*;

import com.api.jaebichuri.auction.enums.AuctionCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class OngoingProductDetailsDto {

    @Schema(description = "경매 상품 식별자 ID", example = "1")
    private Long productId;

    @Schema(description = "경매 식별자 ID", example = "1")
    private Long auctionId;

    @Schema(description = "경매 카테고리", example = "CLOTHING")
    private AuctionCategory auctionCategory;

    @Schema(description = "상품 사진 URL 목록")
    private List<String> images;

    @Schema(description = "상품명", example = "모자")
    private String productName;

    @Schema(description = "상품 설명", example = "모자에 대한 설명입니다.")
    private String productDescription;

    @Schema(description = "경매 등록자 닉네임", example = "에코마켓")
    private String sellerNickname;

    @Schema(description = "경매 마감 시간", example = "")
    private LocalDateTime endTime;

    @Schema(description = "경매에 참여한 입찰자 수", example = "12")
    private Long numOfBidders;

    @Schema(description = "입찰 내역 최근 3개")
    private List<BidDatePriceResponse> top3BidDatePrice;

    @Schema(description = "날짜별 거래량")
    private List<BidVolumeResponse> bidVolumeByDate;
}