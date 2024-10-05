package com.api.jaebichuri.auction.mapper;

import com.api.jaebichuri.auction.dto.ProductDto;
import com.api.jaebichuri.auction.entity.Auction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface AuctionMapper {

    AuctionMapper INSTANCE = Mappers.getMapper(AuctionMapper.class);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.productName", target = "productName")
    @Mapping(source = "product.productDescription", target = "productDescription")
    ProductDto toProductDto(Auction auction);

    default List<ProductDto> toOngoingProductDtoList(List<Auction> auctions) {
        return auctions.stream()
                .map(auction -> {
                    ProductDto responseDto = toProductDto(auction);
                    responseDto.setStartTime(null);
                    responseDto.setEndTime(auction.getEndTime().format(formatter));
                    return responseDto;
                })
                .collect(Collectors.toList());
    }

    default List<ProductDto> toUpcomingProductDtoList(List<Auction> auctions) {
        return auctions.stream()
                .map(auction -> {
                    ProductDto responseDto = toProductDto(auction);
                    responseDto.setStartTime(auction.getStartTime().format(formatter));
                    responseDto.setEndTime(null);
                    return responseDto;
                })
                .collect(Collectors.toList());
    }

}
