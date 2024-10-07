package com.api.jaebichuri.auction.mapper;

import com.api.jaebichuri.auction.dto.EndedAuctionDto;
import com.api.jaebichuri.auction.dto.ScreeningDto;
import com.api.jaebichuri.auction.dto.ScreeningListDto;
import com.api.jaebichuri.auction.entity.AuctionScreening;
import com.api.jaebichuri.auction.entity.EndedAuction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ScreeningMapper {

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "screeningStatus", constant = "PENDING")
    AuctionScreening toAuctionScreening(ScreeningDto screeningDto);

    @Mapping(source = "id", target = "screeningId")
    ScreeningListDto toScreeningListDto(AuctionScreening auctionScreening);

    List<ScreeningListDto> toScreeningListDto(List<AuctionScreening> auctionScreenings);

    @Mapping(source = "id", target = "endedAuctionId")
    EndedAuctionDto toEndedAuctionDto(EndedAuction endedAuction);

    List<EndedAuctionDto> toEndedAuctionDto(List<EndedAuction> endedAuctions);


}
