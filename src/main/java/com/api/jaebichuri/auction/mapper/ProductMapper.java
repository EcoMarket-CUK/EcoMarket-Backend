package com.api.jaebichuri.auction.mapper;

import com.api.jaebichuri.auction.dto.UpcomingAuctionProductDto;
import com.api.jaebichuri.auction.entity.Auction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.productName", target = "productName")
    @Mapping(source = "product.productDescription", target = "productDescription")
    @Mapping(target = "startTime", expression = "java(auction.getStartTime().format(formatter))")
    @Mapping(target=  "endTime", expression = "java(auction.getEndTime().format(formatter))")
    @Mapping(source = "seller.nickname", target = "sellerNickname")
    UpcomingAuctionProductDto auctionToUpcomingAuctionProductDto(Auction auction);

}
