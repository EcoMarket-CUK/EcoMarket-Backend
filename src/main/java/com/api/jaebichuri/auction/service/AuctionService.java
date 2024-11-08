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

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuctionService {

    private final AuctionRepository auctionRepository;
    private final AuctionMapper auctionMapper;

    @Transactional(readOnly = true)
    public List<?> getTopAuctionsByStatus(AuctionStatus status, AuctionCategory category) {
        List<Auction> auctions;

        if (category == null) {
            auctions = auctionRepository.findTop5ByAuctionStatusOrderByCreatedAtDesc(status);
        } else {
            auctions = auctionRepository.findTop5ByAuctionStatusAndAuctionCategoryOrderByCreatedAtDesc(status, category);
        }

        if (status == AuctionStatus.ONGOING) {
            return auctionMapper.toOngoingProductDtoList(auctions);
        } else if (status == AuctionStatus.UPCOMING) {
            return auctionMapper.toUpcomingProductDtoList(auctions);
        } else {
            return Collections.emptyList();
        }
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

    @Transactional(readOnly = true)
    public List<OngoingAuctionProductDto> searchOngoingAuctions(String keyword) {
        List<Auction> auctions = auctionRepository.findByProduct_ProductNameContainingAndAuctionStatusOrderByCreatedAtDesc(keyword, AuctionStatus.ONGOING);

        return auctionMapper.toOngoingProductDtoList(auctions);
    }

}

