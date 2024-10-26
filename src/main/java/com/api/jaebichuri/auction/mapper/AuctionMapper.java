package com.api.jaebichuri.auction.mapper;

import com.api.jaebichuri.auction.dto.OngoingAuctionProductDto;
import com.api.jaebichuri.auction.dto.UpcomingAuctionProductDto;
import com.api.jaebichuri.auction.entity.Auction;
import com.api.jaebichuri.product.entity.AuctionProductImage;
import com.api.jaebichuri.screening.dto.EndedAuctionProductDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AuctionMapper {

    AuctionMapper INSTANCE = Mappers.getMapper(AuctionMapper.class);

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.productName", target = "productName")
    @Mapping(source = "product.productDescription", target = "productDescription")
    @Mapping(target = "imageUrl", expression = "java(getRepresentativeImageUrl(auction.getProduct().getImages()))")
    OngoingAuctionProductDto toOngoingProductDto(Auction auction);

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.productName", target = "productName")
    @Mapping(source = "startTime", target = "startTime") // 시작 시간을 문자열로 변환
    @Mapping(source = "startPrice", target = "startPrice") // 시작 가격을 문자열로 변환
    @Mapping(target = "imageUrl", expression = "java(getRepresentativeImageUrl(auction.getProduct().getImages()))")
    UpcomingAuctionProductDto toUpcomingProductDto(Auction auction);

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.productName", target = "productName")
    @Mapping(source = "product.productDescription", target = "productDescription")
    @Mapping(target = "imageUrl", expression = "java(getRepresentativeImageUrl(auction.getProduct().getImages()))")
    EndedAuctionProductDto toEndedProductDto(Auction auction);

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

