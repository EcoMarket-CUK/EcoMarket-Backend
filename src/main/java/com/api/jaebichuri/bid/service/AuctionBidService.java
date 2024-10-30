package com.api.jaebichuri.bid.service;

import com.api.jaebichuri.auction.entity.Auction;
import com.api.jaebichuri.auction.repository.AuctionRepository;
import com.api.jaebichuri.bid.dto.AuctionBidHttpResponse;
import com.api.jaebichuri.bid.dto.AuctionBidRequestDto;
import com.api.jaebichuri.bid.dto.AuctionBidSocketResponse;
import com.api.jaebichuri.bid.entity.AuctionBid;
import com.api.jaebichuri.bid.mapper.AuctionBidMapper;
import com.api.jaebichuri.bid.repository.AuctionBidRepository;
import com.api.jaebichuri.global.aop.DistributedLock;
import com.api.jaebichuri.global.response.code.status.ErrorStatus;
import com.api.jaebichuri.global.response.exception.CustomException;
import com.api.jaebichuri.global.response.exception.CustomSocketException;
import com.api.jaebichuri.member.entity.Member;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuctionBidService {

    private final AuctionBidRepository auctionBidRepository;
    private final AuctionRepository auctionRepository;
    private final AuctionBidMapper auctionBidMapper;

    public AuctionBidHttpResponse getAuctionBid(Long auctionId) {
        Auction auction = auctionRepository.findById(auctionId)
            .orElseThrow(() -> new CustomException(ErrorStatus._AUCTION_NOT_FOUND));

        Long numOfBidders = auction.getNumOfBidders();

        Optional<AuctionBid> optionalTopAuctionBid = auctionBidRepository.findTopByAuctionOrderByBidPriceDesc(
            auction);

        if (optionalTopAuctionBid.isPresent()) {
            AuctionBid topAuctionBid = optionalTopAuctionBid.get();
            Long canBidPrice = calculateCanBidPrice(topAuctionBid.getBidPrice());

            return auctionBidMapper.toHttpResponse(auction, topAuctionBid, canBidPrice,
                numOfBidders);
        }
        return auctionBidMapper.toHttpResponse(auction);
    }

    @DistributedLock(key = "auctionId")
    @Transactional
    public AuctionBidSocketResponse saveAuctionBid(Member member, Long auctionId,
        AuctionBidRequestDto requestDto) {
        Auction auction = auctionRepository.findById(auctionId)
            .orElseThrow(() -> new CustomSocketException(ErrorStatus._AUCTION_NOT_FOUND,
                member.getId()));

        Long requestBidPrice = requestDto.getBidPrice();

        Optional<AuctionBid> optionalTopAuctionBid = auctionBidRepository.findTopByAuctionOrderByBidPriceDesc(
            auction);

        // 아직 입찰 내역(AuctionBid)가 없다면 해당 경매의 시작 입찰가와 비교해서 입찰 시도
        if (optionalTopAuctionBid.isEmpty()) {
            return handleFirstAuctionBid(member, auction, requestBidPrice);
        }

        // 기존 최고 입찰 내역과 비교 입찰 시도
        AuctionBid topAuctionBid = optionalTopAuctionBid.get();

        // 입찰 가능한지 조건 검증
        validateAuctionBid(member, topAuctionBid, requestBidPrice);

        return handleNewAuctionBid(member, auction, requestBidPrice);
    }

    private AuctionBidSocketResponse handleFirstAuctionBid(Member member, Auction auction,
        Long requestBidPrice) {
        Long startPrice = auction.getStartPrice();

        if (startPrice > requestBidPrice) { // 시작 입찰가보다 낮은 가격이라면 입찰 실패
            throw new CustomSocketException(ErrorStatus._AUCTION_BID_BELOW_STARTING_PRICE,
                member.getId());
        }

        AuctionBid auctionBid = AuctionBid.builder()
            .bidPrice(requestBidPrice)
            .auction(auction)
            .bidder(member)
            .build();
        auctionBidRepository.save(auctionBid);

        Long responseCanBidPrice = calculateCanBidPrice(auctionBid.getBidPrice());

        return auctionBidMapper.toSocketResponse(requestBidPrice, responseCanBidPrice,
            auctionBid.getBidder().getId(), member.getId(), auction.getId(),
            auction.updateNumOfBidders().getNumOfBidders());
    }

    private void validateAuctionBid(Member member, AuctionBid topAuctionBid, Long requestBidPrice) {
        // 이미 최고 입찰가로 입찰 중인 사용자가 입찰 요청을 한 경우
        if (topAuctionBid.getBidder().getId().equals(member.getId())) {
            throw new CustomSocketException(ErrorStatus._AUCTION_BID_ALREADY_HIGHEST,
                member.getId());
        }

        Long canBidPrice = calculateCanBidPrice(topAuctionBid.getBidPrice());

        // 요청 입찰가가 입찰 가능가 보다 작은 경우
        if (canBidPrice > requestBidPrice) {
            throw new CustomSocketException(ErrorStatus._AUCTION_BID_BELOW_MINIMUM_INCREMENT,
                member.getId());
        }
    }

    private AuctionBidSocketResponse handleNewAuctionBid(Member member, Auction auction,
        Long requestBidPrice) {
        boolean isNotNewBidder = auctionBidRepository.existsByBidderAndAuction(member, auction);

        AuctionBid auctionBid = AuctionBid.builder()
            .bidPrice(requestBidPrice)
            .auction(auction)
            .bidder(member)
            .build();
        auctionBidRepository.save(auctionBid);

        Long responseCanBidPrice = calculateCanBidPrice(auctionBid.getBidPrice());

        Long numOfParticipants = isNotNewBidder ? auction.getNumOfBidders()
            : auction.updateNumOfBidders().getNumOfBidders();

        return auctionBidMapper.toSocketResponse(requestBidPrice, responseCanBidPrice,
            auctionBid.getBidder().getId(), member.getId(), auction.getId(), numOfParticipants);
    }

    private Long calculateCanBidPrice(Long nowBestBidPrice) {
        BigDecimal minimumAllowedBid = new BigDecimal(nowBestBidPrice)
            .multiply(new BigDecimal("1.1"))
            .setScale(0, RoundingMode.HALF_UP);

        return minimumAllowedBid.longValue();
    }
}