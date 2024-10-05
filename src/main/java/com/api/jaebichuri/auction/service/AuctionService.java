package com.api.jaebichuri.auction.service;

import com.api.jaebichuri.auction.dto.ProductDto;
import com.api.jaebichuri.auction.entity.Auction;
import com.api.jaebichuri.auction.enums.AuctionCategory;
import com.api.jaebichuri.auction.enums.AuctionStatus;
import com.api.jaebichuri.auction.mapper.AuctionMapper;
import com.api.jaebichuri.auction.repository.AuctionRepository;
import com.api.jaebichuri.auction.repository.ProductRepository;
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
    public List<ProductDto> getOngoingAuctions(AuctionCategory category) {
        List<Auction> ongoingAuctions;

        if(category == null) {
            ongoingAuctions = auctionRepository.findTop5ByAuctionStatusOrderByStartTimeDesc(AuctionStatus.ONGOING);
        } else {
            ongoingAuctions = auctionRepository.findTop5ByAuctionStatusAndAuctionCategoryOrderByStartTimeDesc(AuctionStatus.ONGOING, category);
        }

        return auctionMapper.toOngoingProductDtoList(ongoingAuctions);
    }

    @Transactional(readOnly = true)
    public List<ProductDto> getUpcomingAuctions(AuctionCategory category) {
        List<Auction> ongoingAuctions;

        if(category == null) {
            ongoingAuctions = auctionRepository.findTop5ByAuctionStatusOrderByStartTimeDesc(AuctionStatus.UPCOMING);
        } else {
            ongoingAuctions = auctionRepository.findTop5ByAuctionStatusAndAuctionCategoryOrderByStartTimeDesc(AuctionStatus.UPCOMING, category);
        }

        return auctionMapper.toUpcomingProductDtoList(ongoingAuctions);
    }

}
