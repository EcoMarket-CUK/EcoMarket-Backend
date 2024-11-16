package com.api.jaebichuri.auction.service;

import com.api.jaebichuri.auction.dto.OngoingAuctionProductDto;
import com.api.jaebichuri.auction.dto.ParticipationEndedAuctionResponseDto;
import com.api.jaebichuri.auction.dto.ParticipationOngoingAuctionResponseDto;
import com.api.jaebichuri.auction.entity.Auction;
import com.api.jaebichuri.auction.enums.AuctionCategory;
import com.api.jaebichuri.auction.enums.AuctionStatus;
import com.api.jaebichuri.auction.mapper.AuctionMapper;
import com.api.jaebichuri.auction.repository.AuctionRepository;
import com.api.jaebichuri.bid.entity.AuctionBid;
import com.api.jaebichuri.bid.repository.AuctionBidRepository;
import com.api.jaebichuri.member.entity.Member;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuctionService {

    private final AuctionBidRepository auctionBidRepository;
    private final AuctionRepository auctionRepository;
    private final AuctionMapper auctionMapper;

    @Transactional(readOnly = true)
    public List<?> getTopAuctionsByStatus(AuctionStatus status, AuctionCategory category) {
        List<Auction> auctions;

        if (category == null) {
            auctions = auctionRepository.findTop5ByAuctionStatusOrderByCreatedAtDesc(status);
        } else {
            auctions = auctionRepository.findTop5ByAuctionStatusAndAuctionCategoryOrderByCreatedAtDesc(
                status, category);
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

        if (category == null) {
            ongoingAuctions = auctionRepository.findAllByAuctionStatusOrderByCreatedAtDesc(
                AuctionStatus.ONGOING);
        } else {
            ongoingAuctions = auctionRepository.findAllByAuctionStatusAndAuctionCategoryOrderByCreatedAtDesc(
                AuctionStatus.ONGOING, category);
        }

        return auctionMapper.toOngoingProductDtoList(ongoingAuctions);
    }

    @Transactional(readOnly = true)
    public List<OngoingAuctionProductDto> searchOngoingAuctions(String keyword) {
        List<Auction> auctions = auctionRepository.findByProduct_ProductNameContainingAndAuctionStatusOrderByCreatedAtDesc(
            keyword, AuctionStatus.ONGOING);

        return auctionMapper.toOngoingProductDtoList(auctions);
    }

    @Transactional(readOnly = true)
    public List<?> getParticipateAuction(Member member, AuctionStatus auctionStatus) {
        List<AuctionBid> auctionBidList = auctionBidRepository.findAllByBidderAndAuctionStatus(
            member, auctionStatus);

        if (auctionBidList.isEmpty()) {
            return new ArrayList<>();
        }

        Map<Auction, Long> auctionPriceMap = new HashMap<>();
        Map<Auction, String> auctionDateMap = new HashMap<>();

        updateMap(auctionStatus, auctionBidList, auctionPriceMap, auctionDateMap);

        return createResponse(auctionStatus, auctionPriceMap, auctionDateMap);
    }

    private static void updateMap(AuctionStatus auctionStatus, List<AuctionBid> auctionBidList,
        Map<Auction, Long> auctionPriceMap, Map<Auction, String> auctionDateMap) {
        auctionBidList.forEach(
            auctionBid -> {
                Auction auction = auctionBid.getAuction();
                if (auctionPriceMap.containsKey(auction)) {
                    if (auctionPriceMap.get(auction) < auctionBid.getBidPrice()) {
                        auctionPriceMap.put(auction, auctionBid.getBidPrice());
                        if (auctionStatus == AuctionStatus.ENDED) {
                            auctionDateMap.put(auction, auctionBid.getCreatedAt()
                                .format(DateTimeFormatter.ofPattern("MM")));
                        }
                    }
                } else {
                    auctionPriceMap.put(auction, auctionBid.getBidPrice());
                    if (auctionStatus == AuctionStatus.ENDED) {
                        auctionDateMap.put(auction,
                            auctionBid.getCreatedAt().format(DateTimeFormatter.ofPattern("MM")));
                    }
                }
            }
        );
    }

    private List<Object> createResponse(AuctionStatus auctionStatus,
        Map<Auction, Long> auctionPriceMap, Map<Auction, String> auctionDateMap) {

        List<Object> participateAuctionResponseDtoList = new ArrayList<>();

        auctionPriceMap.keySet()
            .forEach(auction -> {
                if (auctionStatus == AuctionStatus.ONGOING) {
                    ParticipationOngoingAuctionResponseDto participationOngoingAuctionResponseDto = auctionMapper.toParticipationOngoingAuctionResponseDto(
                        auction, auctionPriceMap.get(auction));
                    participateAuctionResponseDtoList.add(participationOngoingAuctionResponseDto);
                } else if (auctionStatus == AuctionStatus.ENDED) {
                    ParticipationEndedAuctionResponseDto participationEndedAuctionResponseDto = auctionMapper.toParticipationEndedAuctionResponseDto(
                        auctionDateMap.get(auction),
                        auctionPriceMap.get(auction).equals(auction.getCurrentPrice()), auction,
                        auctionPriceMap.get(auction));
                    participateAuctionResponseDtoList.add(participationEndedAuctionResponseDto);
                }
            });

        return participateAuctionResponseDtoList;
    }
}