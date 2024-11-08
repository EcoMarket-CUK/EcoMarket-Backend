package com.api.jaebichuri.auction.service;

import com.api.jaebichuri.auction.entity.Auction;
import com.api.jaebichuri.auction.enums.AuctionStatus;
import com.api.jaebichuri.auction.repository.AuctionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuctionScheduler {

    private final AuctionRepository auctionRepository;

    @Scheduled(fixedRate = 60000)
    @Transactional
    public void updateAuctionStatus() {
        LocalDateTime now = LocalDateTime.now();

        List<Auction> upcomingAuctions = auctionRepository.findByAuctionStatusAndStartTimeLessThanEqual(AuctionStatus.UPCOMING, now);
        for (Auction auction : upcomingAuctions) {
            auction.startAuction();
        }

        List<Auction> ongoingAuctions = auctionRepository.findByAuctionStatusAndEndTimeLessThanEqual(AuctionStatus.ONGOING, now);
        for (Auction auction : ongoingAuctions) {
            auction.endAuction();
        }
    }

}
