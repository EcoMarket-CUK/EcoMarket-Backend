package com.api.jaebichuri.auction.mapper;

import com.api.jaebichuri.auction.dto.OngoingAuctionProductDto;
import com.api.jaebichuri.auction.dto.ParticipationEndedAuctionResponseDto;
import com.api.jaebichuri.auction.dto.ParticipationOngoingAuctionResponseDto;
import com.api.jaebichuri.auction.dto.UpcomingAuctionProductDto;
import com.api.jaebichuri.auction.entity.Auction;
import com.api.jaebichuri.product.entity.AuctionProductImage;
import com.api.jaebichuri.screening.dto.EndedAuctionProductDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Mapper(componentModel = "spring")
public interface AuctionMapper {

    AuctionMapper INSTANCE = Mappers.getMapper(AuctionMapper.class);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.productName", target = "productName")
    @Mapping(source = "product.productDescription", target = "productDescription")
    @Mapping(target = "imageUrl", expression = "java(getRepresentativeImageUrl(auction.getProduct().getImages()))")
    OngoingAuctionProductDto toOngoingProductDto(Auction auction);

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.productName", target = "productName")
    @Mapping(source = "startTime", target = "startTime", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @Mapping(target = "imageUrl", expression = "java(getRepresentativeImageUrl(auction.getProduct().getImages()))")
    UpcomingAuctionProductDto toUpcomingProductDto(Auction auction);

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.productName", target = "productName")
    @Mapping(source = "product.productDescription", target = "productDescription")
    @Mapping(target = "imageUrl", expression = "java(getRepresentativeImageUrl(auction.getProduct().getImages()))")
    EndedAuctionProductDto toEndedProductDto(Auction auction);

    @Mapping(target = "imageUrl", expression = "java(getRepresentativeImageUrl(auction.getProduct().getImages()))")
    @Mapping(source = "auction.auctionCategory", target = "auctionCategory")
    @Mapping(source = "myBidPrice", target = "myBidPrice")
    @Mapping(source = "auction.currentPrice", target = "topBidPrice")
    @Mapping(source = "auction.product.productDescription", target = "productDescription")
    @Mapping(source = "auction.product.productName", target = "productName")
    @Mapping(source = "auction.product.id", target = "productId")
    ParticipationOngoingAuctionResponseDto toParticipationOngoingAuctionResponseDto(Auction auction, Long myBidPrice);

    @Mapping(source = "month", target = "month")
    @Mapping(source = "isSuccessBid", target = "isSuccessBid")
    @Mapping(target = "imageUrl", expression = "java(getRepresentativeImageUrl(auction.getProduct().getImages()))")
    @Mapping(source = "myBidPrice", target = "myBidPrice")
    @Mapping(source = "auction.currentPrice", target = "topBidPrice")
    @Mapping(source = "auction.product.productDescription", target = "productDescription")
    @Mapping(source = "auction.product.productName", target = "productName")
    @Mapping(source = "auction.product.id", target = "productId")
    ParticipationEndedAuctionResponseDto toParticipationEndedAuctionResponseDto(String month, Boolean isSuccessBid, Auction auction, Long myBidPrice);

    List<EndedAuctionProductDto> toEndedProductDtoList(List<Auction> auctions);

    List<OngoingAuctionProductDto> toOngoingProductDtoList(List<Auction> auctions);

    List<UpcomingAuctionProductDto> toUpcomingProductDtoList(List<Auction> auctions);

    default String getRepresentativeImageUrl(List<AuctionProductImage> images) {
        return images.stream()
                .filter(AuctionProductImage::getIsRepresentative)
                .map(AuctionProductImage::getImageUrl)
                .findFirst()
                .orElse(null);
    }

}
