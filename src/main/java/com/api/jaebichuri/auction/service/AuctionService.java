package com.api.jaebichuri.auction.service;

import com.api.jaebichuri.auction.dto.OngoingAuctionProductDto;
import com.api.jaebichuri.auction.dto.UpcomingAuctionProductDto;
import com.api.jaebichuri.auction.entity.Auction;
import com.api.jaebichuri.auction.enums.AuctionCategory;
import com.api.jaebichuri.auction.enums.AuctionStatus;
import com.api.jaebichuri.auction.mapper.AuctionMapper;
import com.api.jaebichuri.auction.repository.AuctionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuctionService {

    private final AuctionRepository auctionRepository;
    private final AuctionMapper auctionMapper;

    @Transactional(readOnly = true)
    public List<OngoingAuctionProductDto> getTopOngoingAuctions(AuctionCategory category) {
        List<Auction> ongoingAuctions;

        if (category == null) {
            ongoingAuctions = auctionRepository.findTop5ByAuctionStatusOrderByCreatedAtDesc(AuctionStatus.ONGOING);
        } else {
            ongoingAuctions = auctionRepository.findTop5ByAuctionStatusAndAuctionCategoryOrderByCreatedAtDesc(AuctionStatus.ONGOING, category);
        }

        return auctionMapper.toOngoingProductDtoList(ongoingAuctions);
    }

    @Transactional(readOnly = true)
    public List<UpcomingAuctionProductDto> getTopUpcomingAuctions(AuctionCategory category) {
        List<Auction> upcomingAuctions;

        if (category == null) {
            upcomingAuctions = auctionRepository.findTop5ByAuctionStatusOrderByCreatedAtDesc(AuctionStatus.UPCOMING);
        } else {
            upcomingAuctions = auctionRepository.findTop5ByAuctionStatusAndAuctionCategoryOrderByCreatedAtDesc(AuctionStatus.UPCOMING, category);
        }

        return auctionMapper.toUpcomingProductDtoList(upcomingAuctions);
    }

    @Transactional(readOnly = true)
    public List<OngoingAuctionProductDto> getAllOngoingAuctions(AuctionCategory category) {
        List<Auction> ongoingAuctions;

        if(category == null) {
            ongoingAuctions = auctionRepository.findAllByAuctionStatusOrderByCreatedAtDesc(AuctionStatus.ONGOING);
        } else {
            ongoingAuctions = auctionRepository.findAllByAuctionStatusAndAuctionCategoryOrderByCreatedAtDesc(AuctionStatus.ONGOING, category);
        }

        return auctionMapper.toOngoingProductDtoList(ongoingAuctions);
    }

}
