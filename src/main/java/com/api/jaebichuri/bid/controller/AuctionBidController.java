package com.api.jaebichuri.bid.controller;

import com.api.jaebichuri.bid.service.BidStompService;
import com.api.jaebichuri.bid.dto.AuctionBidRequestDto;
import com.api.jaebichuri.bid.dto.AuctionBidHttpResponse;
import com.api.jaebichuri.bid.dto.AuctionBidSocketResponse;
import com.api.jaebichuri.bid.service.AuctionBidService;
import com.api.jaebichuri.global.response.ApiResponse;
import com.api.jaebichuri.member.entity.Member;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "경매 API")
public class AuctionBidController {

    private final AuctionBidService auctionBidService;
    private final BidStompService bidStompService;

    @GetMapping("/auctions/{auctionId}/auction-bid")
    @Operation(summary = "경매 입장 API")
    public ResponseEntity<ApiResponse<AuctionBidHttpResponse>> getAuctionBid(
        @PathVariable Long auctionId) {
        return ResponseEntity.ok(ApiResponse.onSuccess(auctionBidService.getAuctionBid(auctionId)));
    }

    @MessageMapping("/bid/auctions/{auctionId}")
    public void saveAuctionBid(Authentication authentication, @DestinationVariable Long auctionId,
        @Payload AuctionBidRequestDto requestDto) {
        Member member = (Member) authentication.getPrincipal();
        AuctionBidSocketResponse socketResponse = auctionBidService.saveAuctionBid(member,
            auctionId, requestDto);
        bidStompService.bidPub(socketResponse);
    }
}