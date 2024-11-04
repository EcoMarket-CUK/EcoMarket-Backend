package com.api.jaebichuri.bid.mapper;

import com.api.jaebichuri.auction.entity.Auction;
import com.api.jaebichuri.bid.dto.AuctionBidHttpResponse;
import com.api.jaebichuri.bid.dto.AuctionBidSocketResponse;
import com.api.jaebichuri.bid.dto.AuctionBidSocketResponse.AuctionBidSubscribersResponse;
import com.api.jaebichuri.bid.dto.AuctionBidSocketResponse.AuctionBidderResponse;
import com.api.jaebichuri.bid.entity.AuctionBid;
import com.api.jaebichuri.global.response.code.status.ErrorStatus;
import com.api.jaebichuri.global.response.code.status.SuccessStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AuctionBidMapper {

    @Mapping(source = "auction.startPrice", target = "startBidPrice")
    @Mapping(source = "auction.endTime", target = "endTime")
    @Mapping(source = "auctionBid.bidPrice", target = "topBidPrice")
    @Mapping(source = "canBidPrice", target = "canBidPrice")
    @Mapping(source = "numOfBidders", target = "numOfBidders")
    AuctionBidHttpResponse toHttpResponse(Auction auction, AuctionBid auctionBid,
        Long canBidPrice, Long numOfBidders);

    @Mapping(source = "auction.startPrice", target = "startBidPrice")
    @Mapping(source = "auction.startPrice", target = "canBidPrice")
    @Mapping(source = "auction.endTime", target = "endTime")
    @Mapping(constant = "0L", target = "topBidPrice")
    @Mapping(constant = "0L", target = "numOfBidders")
    AuctionBidHttpResponse toHttpResponse(Auction auction);

    @Mapping(source = "topBidPrice", target = "topBidPrice")
    @Mapping(source = "canBidPrice", target = "canBidPrice")
    @Mapping(source = "previousBidderId", target = "previousBidderId")
    @Mapping(source = "newBidderId", target = "newBidderId")
    @Mapping(source = "auctionId", target = "auctionId")
    @Mapping(source = "numOfBidders", target = "numOfBidders")
    AuctionBidSocketResponse toSocketResponse(Long topBidPrice, Long canBidPrice,
        Long previousBidderId, Long newBidderId, Long auctionId, Long numOfBidders);

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
    AuctionBidSubscribersResponse toSubscribersResponse(AuctionBidSocketResponse socketResponse);

    default String getSuccessCode() {
        return SuccessStatus._OK.getCode();
    }
}