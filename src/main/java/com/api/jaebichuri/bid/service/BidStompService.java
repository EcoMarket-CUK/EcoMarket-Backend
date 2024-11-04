package com.api.jaebichuri.bid.service;

import com.api.jaebichuri.bid.dto.AuctionBidSocketResponse;
import com.api.jaebichuri.bid.dto.AuctionBidSocketResponse.AuctionBidSubscribersResponse;
import com.api.jaebichuri.bid.dto.AuctionBidSocketResponse.AuctionBidderResponse;
import com.api.jaebichuri.bid.mapper.AuctionBidMapper;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BidStompService {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final AuctionBidMapper auctionBidMapper;

    private static final String AUCTION_TOPIC_PREFIX = "/sub/auctions/";
    private static final String MEMBER_TOPIC_PREFIX = "/sub/members/";

    public void bidPub(AuctionBidSocketResponse socketResponse) {
        // 해당 경매를 구독중인 모든 멤버에게 pub
        sendToAllAuctionSubscribers(socketResponse);

        // 해당 경매에 입찰 요청한 멤버에게 pub
        sendToNewBidder(socketResponse);

        // 이전 최고 입찰자에게 pub
        sendToPreviousBidder(socketResponse);
    }

    private void sendToAllAuctionSubscribers(AuctionBidSocketResponse socketResponse) {
        AuctionBidSubscribersResponse subscribersResponse = auctionBidMapper.toSubscribersResponse(
            socketResponse);
        simpMessagingTemplate.convertAndSend(AUCTION_TOPIC_PREFIX + socketResponse.getAuctionId(),
            subscribersResponse);
    }

    private void sendToNewBidder(AuctionBidSocketResponse socketResponse) {
        AuctionBidderResponse bidderResponse = auctionBidMapper.toSuccessResponse("입찰에 성공했습니다.");
        simpMessagingTemplate.convertAndSend(MEMBER_TOPIC_PREFIX + socketResponse.getNewBidderId(),
            bidderResponse);
    }

    private void sendToPreviousBidder(AuctionBidSocketResponse socketResponse) {
        if (Objects.nonNull(socketResponse.getPreviousBidderId())
            && !socketResponse.getPreviousBidderId().equals(socketResponse.getNewBidderId())) {
            AuctionBidderResponse takeAuctionBidResponse = auctionBidMapper.toSuccessResponse(
                "새로운 최고 입찰자로 인해 최고 입찰자의 자리가 빼앗겼습니다.");
            simpMessagingTemplate.convertAndSend(
                MEMBER_TOPIC_PREFIX + socketResponse.getPreviousBidderId(), takeAuctionBidResponse);
        }
    }
}