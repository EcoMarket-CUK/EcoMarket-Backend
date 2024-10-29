package com.api.jaebichuri.screening.mapper;

import com.api.jaebichuri.screening.dto.ScreeningDto;
import com.api.jaebichuri.screening.dto.ScreeningListDto;
import com.api.jaebichuri.screening.entity.AuctionScreening;
import com.api.jaebichuri.screening.entity.AuctionScreeningImage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ScreeningMapper {

    @Mapping(target = "screeningStatus", constant = "PENDING")
    AuctionScreening toAuctionScreening(ScreeningDto screeningDto);

    @Mapping(source = "id", target = "screeningId")
    @Mapping(target = "imageUrl", expression = "java(getRepresentativeImageUrl(auctionScreening.getImages()))")
    ScreeningListDto toScreeningListDto(AuctionScreening auctionScreening);

    List<ScreeningListDto> toScreeningListDto(List<AuctionScreening> auctionScreenings);

    default String getRepresentativeImageUrl(List<AuctionScreeningImage> images) {
        return images.stream()
                .filter(AuctionScreeningImage::getIsRepresentative) // 대표 이미지 필터링
                .map(AuctionScreeningImage::getImageUrl)
                .findFirst()
                .orElse(null);
    }

}
