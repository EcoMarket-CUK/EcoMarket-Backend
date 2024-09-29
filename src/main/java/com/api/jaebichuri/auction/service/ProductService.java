package com.api.jaebichuri.auction.service;

import com.api.jaebichuri.auction.dto.UpcomingAuctionProductDto;
import com.api.jaebichuri.auction.dto.ProductResponseDto;
import com.api.jaebichuri.auction.entity.Auction;
import com.api.jaebichuri.auction.enums.AuctionStatus;
import com.api.jaebichuri.auction.mapper.AuctionMapper;
import com.api.jaebichuri.auction.repository.AuctionRepository;
import com.api.jaebichuri.global.response.code.status.ErrorStatus;
import com.api.jaebichuri.global.response.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final AuctionRepository auctionRepository;
    private final AuctionMapper auctionMapper;

    @Transactional(readOnly = true)
    public List<ProductResponseDto> getAllAuctionProducts() {
        List<Auction> auctions = auctionRepository.findAll();

        return auctionMapper.toProductResponseDtoList(auctions);
    }

    @Transactional(readOnly = true)
    public UpcomingAuctionProductDto getUpcomingAuctionProductDetails(long auctionId) {
        Auction auction = auctionRepository.findByIdAndAuctionStatus(auctionId, AuctionStatus.UPCOMING)
                .orElseThrow(() -> new CustomException(ErrorStatus._AUCTION_NOT_FOUND));

        return auctionMapper.auctionToUpcomingAuctionProductDto(auction);
    }

}
