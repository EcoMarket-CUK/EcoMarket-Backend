package com.api.jaebichuri.auction.mapper;

import com.api.jaebichuri.auction.dto.UpcomingAuctionProductDto;
import com.api.jaebichuri.auction.dto.ProductResponseDto;
import com.api.jaebichuri.auction.entity.Auction;
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
    @Mapping(source = "seller.nickname", target = "sellerNickname")
    UpcomingAuctionProductDto auctionToUpcomingAuctionProductDto(Auction auction);

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.productName", target = "productName")
    @Mapping(source = "seller.nickname", target = "sellerNickname")
    ProductResponseDto auctionToProductResponseDto(Auction auction);

    List<ProductResponseDto> toProductResponseDtoList(List<Auction> auctions);

}
