package com.api.jaebichuri.auction.service;

import com.api.jaebichuri.auction.dto.UpcomingAuctionProductDto;
import com.api.jaebichuri.auction.entity.Auction;
import com.api.jaebichuri.auction.enums.AuctionStatus;
import com.api.jaebichuri.auction.mapper.ProductMapper;
import com.api.jaebichuri.auction.repository.AuctionRepository;
import com.api.jaebichuri.global.response.code.status.ErrorStatus;
import com.api.jaebichuri.global.response.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class ProductService {

    private final AuctionRepository auctionRepository;
    private final ProductMapper productMapper;

    @Transactional(readOnly = true)
    public UpcomingAuctionProductDto getUpcomingAuctionProductDetails(Long auctionId) {
        Auction auction = auctionRepository.findByIdAndAuctionStatus(auctionId, AuctionStatus.UPCOMING)
                .orElseThrow(() -> new CustomException(ErrorStatus._AUCTION_NOT_FOUND));

        return productMapper.auctionToUpcomingAuctionProductDto(auction);
    }

}
