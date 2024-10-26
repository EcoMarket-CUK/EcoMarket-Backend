package com.api.jaebichuri.auction.service;

import com.api.jaebichuri.auction.dto.EndedAuctionDto;
import com.api.jaebichuri.auction.dto.ScreeningDto;
import com.api.jaebichuri.auction.dto.ScreeningListDto;
import com.api.jaebichuri.auction.entity.AuctionScreening;
import com.api.jaebichuri.auction.entity.EndedAuction;
import com.api.jaebichuri.auction.enums.AuctionScreeningStatus;
import com.api.jaebichuri.auction.mapper.ScreeningMapper;
import com.api.jaebichuri.auction.repository.EndedAuctionRepository;
import com.api.jaebichuri.auction.repository.ScreeningRepository;
import com.api.jaebichuri.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScreeningService {

    private final ScreeningRepository screeningRepository;
    private final EndedAuctionRepository endedAuctionRepository;
    private final ScreeningMapper screeningMapper;

    @Transactional
    public String createAuctionScreening(ScreeningDto screeningDto, Member member) {
        AuctionScreening auctionScreening = screeningMapper.toAuctionScreening(screeningDto);
        auctionScreening.setSeller(member);

        screeningRepository.save(auctionScreening);

        return "Auction screening has been created";
    }

    @Transactional(readOnly = true)
    public List<ScreeningListDto> getAuctionScreenings(Member member) {
        List<AuctionScreening> screenings = screeningRepository.findBySellerAndScreeningStatus(member, AuctionScreeningStatus.PENDING);

        return screeningMapper.toScreeningListDto(screenings);
    }

    @Transactional(readOnly = true)
    public List<EndedAuctionDto> getEndedAuctions(Member member) {
        List<EndedAuction> endedAuctions = endedAuctionRepository.findBySeller(member);

        return screeningMapper.toEndedAuctionDto(endedAuctions);
    }

}
