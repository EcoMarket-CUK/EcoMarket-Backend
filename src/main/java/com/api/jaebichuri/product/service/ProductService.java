package com.api.jaebichuri.product.service;

import com.api.jaebichuri.bid.dto.AuctionBidHttpResponse.BidDatePriceResponse;
import com.api.jaebichuri.bid.dto.AuctionBidHttpResponse.BidVolumeResponse;
import com.api.jaebichuri.bid.service.AuctionBidService;
import com.api.jaebichuri.member.entity.Member;
import com.api.jaebichuri.product.dto.EndedProductDetailsDto;
import com.api.jaebichuri.product.dto.OngoingProductDetailsDto;
import com.api.jaebichuri.product.dto.UpcomingProductDetailsDto;
import com.api.jaebichuri.auction.entity.Auction;
import com.api.jaebichuri.auction.enums.AuctionStatus;
import com.api.jaebichuri.product.mapper.ProductMapper;
import com.api.jaebichuri.auction.repository.AuctionRepository;
import com.api.jaebichuri.global.response.code.status.ErrorStatus;
import com.api.jaebichuri.global.response.exception.CustomException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final AuctionRepository auctionRepository;
    private final ProductMapper productMapper;
    private final AuctionBidService auctionBidService;

    @Transactional(readOnly = true)
    public UpcomingProductDetailsDto getUpcomingAuctionProductDetails(Long auctionId) {
        Auction auction = auctionRepository.findByIdAndAuctionStatus(auctionId,
                AuctionStatus.UPCOMING)
            .orElseThrow(() -> new CustomException(ErrorStatus._AUCTION_NOT_FOUND));

        return productMapper.auctionToUpcomingAuctionProductDto(auction);
    }

    @Transactional(readOnly = true)
    public EndedProductDetailsDto getEndedAuctionDetails(Long auctionId, Member member) {
        Auction auction = auctionRepository.findByIdAndSeller(auctionId, member)
            .orElseThrow(() -> new CustomException(ErrorStatus._AUCTION_NOT_FOUND));

        EndedProductDetailsDto endedProductDetailsDto = productMapper.toEndedProductDetailsDto(
            auction);

        endedProductDetailsDto.setTop3BidDatePrice(
            auctionBidService.getBidDatePriceResponseList(auction));
        endedProductDetailsDto.setBidVolumeByDate(auctionBidService.getVolumeResponseList(auction));

        return endedProductDetailsDto;
    }

    @Transactional(readOnly = true)
    public OngoingProductDetailsDto getOngoingAuctionDetails(Long auctionId, Member member) {
        Auction auction = auctionRepository.findByIdAndAuctionStatus(auctionId,
                AuctionStatus.ONGOING)
            .orElseThrow(() -> new CustomException(ErrorStatus._AUCTION_NOT_FOUND));

        List<BidDatePriceResponse> bidDatePriceResponseList = auctionBidService.getBidDatePriceResponseList(
            auction);

        List<BidVolumeResponse> volumeResponseList = auctionBidService.getVolumeResponseList(
            auction);

        String sellerNickname = auction.getSeller().getId().equals(member.getId())
            ? auction.getSeller().getNickname() + "(나)" : auction.getSeller().getNickname();

        return productMapper.toOngoingProductDetailsDto(auction, sellerNickname, bidDatePriceResponseList,
            volumeResponseList, member.getId());
    }
}