package com.api.jaebichuri.bid.mapper;

import com.api.jaebichuri.auction.entity.Auction;
import com.api.jaebichuri.bid.dto.AuctionBidHttpResponse;
import com.api.jaebichuri.bid.dto.AuctionBidHttpResponse.BidDatePriceResponse;
import com.api.jaebichuri.bid.dto.AuctionBidHttpResponse.BidVolumeResponse;
import com.api.jaebichuri.bid.dto.AuctionBidSocketResponse;
import com.api.jaebichuri.bid.dto.AuctionBidSocketResponse.AuctionBidSubscribersResponse;
import com.api.jaebichuri.bid.dto.AuctionBidSocketResponse.AuctionBidderResponse;
import com.api.jaebichuri.bid.entity.AuctionBid;
import com.api.jaebichuri.global.response.code.status.ErrorStatus;
import com.api.jaebichuri.global.response.code.status.SuccessStatus;
import com.api.jaebichuri.product.entity.AuctionProduct;
import com.api.jaebichuri.product.entity.AuctionProductImage;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AuctionBidMapper {

    @Mapping(source = "auction.auctionCategory", target = "auctionCategory")
    @Mapping(source = "product.productName", target = "productName")
    @Mapping(source = "product.productDescription", target = "productDescription")
    @Mapping(target = "imageUrl", expression = "java(getRepresentativeImageUrl(auction.getProduct().getImages()))")
    @Mapping(source = "auction.startPrice", target = "startBidPrice")
    @Mapping(source = "auction.endTime", target = "endTime")
    @Mapping(source = "auctionBid.bidPrice", target = "topBidPrice")
    @Mapping(source = "canBidPrice", target = "canBidPrice")
    @Mapping(source = "numOfBidders", target = "numOfBidders")
    @Mapping(source = "bidDatePriceResponseList", target = "top3BidDatePriceList")
    @Mapping(source = "bidVolumeResponseList", target = "bidVolumeResponseList")
    @Mapping(source = "memberId", target = "memberId")
    AuctionBidHttpResponse toHttpResponse(Auction auction, AuctionProduct product, AuctionBid auctionBid,
        Long canBidPrice, Long numOfBidders, List<BidDatePriceResponse> bidDatePriceResponseList,
        List<BidVolumeResponse> bidVolumeResponseList, Long memberId);

    @Mapping(source = "bidDate", target = "bidDate")
    @Mapping(source = "bidPrice", target = "bidPrice")
    BidDatePriceResponse toBidDatePriceResponse(String bidDate, Long bidPrice);

    @Mapping(source = "date", target = "date")
    @Mapping(source = "volume", target = "volume")
    BidVolumeResponse toBidVolumeResponse(String date, Long volume);

    @Mapping(source = "auction.auctionCategory", target = "auctionCategory")
    @Mapping(source = "product.productName", target = "productName")
    @Mapping(source = "product.productDescription", target = "productDescription")
    @Mapping(target = "imageUrl", expression = "java(getRepresentativeImageUrl(auction.getProduct().getImages()))")
    @Mapping(source = "auction.startPrice", target = "startBidPrice")
    @Mapping(source = "auction.startPrice", target = "canBidPrice")
    @Mapping(source = "auction.endTime", target = "endTime")
    @Mapping(constant = "0L", target = "topBidPrice")
    @Mapping(constant = "0L", target = "numOfBidders")
    @Mapping(source = "memberId", target = "memberId")
    AuctionBidHttpResponse toHttpResponse(Auction auction, AuctionProduct product, Long memberId);

    @Mapping(source = "topBidPrice", target = "topBidPrice")
    @Mapping(source = "canBidPrice", target = "canBidPrice")
    @Mapping(source = "previousBidderId", target = "previousBidderId")
    @Mapping(source = "newBidderId", target = "newBidderId")
    @Mapping(source = "auctionId", target = "auctionId")
    @Mapping(source = "numOfBidders", target = "numOfBidders")
    @Mapping(source = "bidVolumeResponse", target = "bidVolumeResponse")
    @Mapping(source = "bidDatePriceResponse", target = "bidDatePriceResponse")
    AuctionBidSocketResponse toSocketResponse(Long topBidPrice, Long canBidPrice,
        Long previousBidderId, Long newBidderId, Long auctionId, Long numOfBidders,
        BidVolumeResponse bidVolumeResponse, BidDatePriceResponse bidDatePriceResponse);

    @Mapping(target = "isSuccess", constant = "true")
    @Mapping(target = "code", expression = "java(getSuccessCode())")
    @Mapping(target = "message", source = "message")
    AuctionBidderResponse toSuccessResponse(String message);

    @Mapping(target = "isSuccess", constant = "false")
    @Mapping(source = "errorStatus.code", target = "code")
    @Mapping(source = "message", target = "message")
    AuctionBidderResponse toFailResponse(ErrorStatus errorStatus, String message);

    @Mapping(source = "socketResponse.topBidPrice", target = "topBidPrice")
    @Mapping(source = "socketResponse.canBidPrice", target = "canBidPrice")
    @Mapping(source = "socketResponse.numOfBidders", target = "numOfBidders")
    @Mapping(source = "socketResponse.bidVolumeResponse", target = "bidVolumeResponse")
    @Mapping(source = "socketResponse.bidDatePriceResponse", target = "bidDatePriceResponse")
    AuctionBidSubscribersResponse toSubscribersResponse(AuctionBidSocketResponse socketResponse);

    default String getSuccessCode() {
        return SuccessStatus._OK.getCode();
    }

    default String getRepresentativeImageUrl(List<AuctionProductImage> images) {
        return images.stream()
            .filter(AuctionProductImage::getIsRepresentative)
            .map(AuctionProductImage::getImageUrl)
            .findFirst()
            .orElse(null);
    }
}