package com.api.jaebichuri.bid.controller;

import com.api.jaebichuri.bid.service.BidStompService;
import com.api.jaebichuri.bid.dto.AuctionBidRequestDto;
import com.api.jaebichuri.bid.dto.AuctionBidHttpResponse;
import com.api.jaebichuri.bid.dto.AuctionBidSocketResponse;
import com.api.jaebichuri.bid.service.AuctionBidService;
import com.api.jaebichuri.global.response.ApiResponse;
import com.api.jaebichuri.member.entity.Member;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Tag(name = "Bid", description = "입찰 관련 API")
public class AuctionBidController {

    private final AuctionBidService auctionBidService;
    private final BidStompService bidStompService;

    @Operation(
        summary = "경매 입찰방 입장 API",
        description = "실시간 입찰이 이루어지는 경매 입찰방 입장 API입니다. 해당 API는 사용자 인증이 요구됩니다.\n\n"
            + "해당 API 이후 소켓 연결, 토픽 구독이 필요합니다.(해당 내용은 노션에 정리)",
        parameters = {
            @Parameter(name = "auctionId", description = "입장할 경매 식별자 ID", required = true)
        },
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "COMMON200",
                description = "해당 경매에 대한 입찰가, 거래내역과 같은 정보",
                content = @Content(schema = @Schema(implementation = AuctionBidHttpResponse.class))
            )
        }
    )
    @GetMapping("/auctions/{auctionId}/auction-bid")
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