package com.api.jaebichuri.product.dto;

import com.api.jaebichuri.bid.dto.AuctionBidHttpResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Schema(description = "마감된 경매 물품 상세 조회에 대한 정보 DTO")
public class EndedProductDetailsDto {

    @Schema(description = "경매 상품 식별자 ID", example = "1")
    private Long productId;

    @Schema(description = "상품명", example = "모자")
    private String productName;

    @Schema(description = "상품명", example = "모자에 대한 설명입니다.")
    private String productDescription;

    @Schema(description = "경매에 참여한 입찰자 수", example = "12")
    private Long numOfBidders;

    @Schema(description = "낙찰 여부 (true: 낙찰, false: 유찰)", example = "true")
    private Boolean isBidSuccessful;

    @Schema(description = "최종 낙찰가", example = "50000")
    private Long finalBidPrice;

    @Schema(description = "경매 등록자 닉네임", example = "에코마켓")
    private String sellerNickname;

    @Schema(description = "경매 등록자 프로필 이미지 URL", example = "http://example.com/profile.jpg")
    private String sellerProfileImage;

    @Schema(description = "상품 사진 URL 목록")
    private List<String> images;

    @Schema(description = "입찰 날짜 및 가격 리스트")
    private List<AuctionBidHttpResponse.BidDatePriceResponse> top3BidDatePrice;

    @Schema(description = "날짜별 입찰 수량 리스트")
    private List<AuctionBidHttpResponse.BidVolumeResponse> bidVolumeByDate;

}
