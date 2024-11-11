package com.api.jaebichuri.screening.mapper;

import com.api.jaebichuri.screening.dto.ScreeningDto;
import com.api.jaebichuri.screening.dto.ScreeningListDto;
import com.api.jaebichuri.screening.entity.AuctionScreening;
import com.api.jaebichuri.screening.entity.AuctionScreeningImage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ScreeningMapper {

    @Mapping(target = "screeningStatus", constant = "PRICE_REVIEW")
    @Mapping(source = "startTime", target = "startTime", qualifiedByName = "stringToLocalDateTime")
    @Mapping(source = "endTime", target = "endTime", qualifiedByName = "stringToLocalDateTime")
    AuctionScreening toAuctionScreening(ScreeningDto screeningDto);

    @Mapping(source = "id", target = "screeningId")
    @Mapping(target = "imageUrl", expression = "java(getRepresentativeImageUrl(auctionScreening.getImages()))")
    ScreeningListDto toScreeningListDto(AuctionScreening auctionScreening);

    List<ScreeningListDto> toScreeningListDto(List<AuctionScreening> auctionScreenings);

    @Named("stringToLocalDateTime")
    default LocalDateTime stringToLocalDateTime(String dateTime) {
        return LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    default String getRepresentativeImageUrl(List<AuctionScreeningImage> images) {
        return images.stream()
                .filter(AuctionScreeningImage::getIsRepresentative)
                .map(AuctionScreeningImage::getImageUrl)
                .findFirst()
                .orElse(null);
    }

}
