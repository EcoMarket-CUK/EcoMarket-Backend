package com.api.jaebichuri.product.mapper;

import com.api.jaebichuri.product.dto.EndedProductDetailsDto;
import com.api.jaebichuri.product.dto.UpcomingProductDetailsDto;
import com.api.jaebichuri.auction.entity.Auction;
import com.api.jaebichuri.product.entity.AuctionProductImage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.productName", target = "productName")
    @Mapping(source = "product.productDescription", target = "productDescription")
    @Mapping(source = "seller.nickname", target = "sellerNickname")
    @Mapping(target = "images", expression = "java(filterRepresentativeImages(auction.getProduct().getImages()))")
    UpcomingProductDetailsDto auctionToUpcomingAuctionProductDto(Auction auction);

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.productName", target = "productName")
    @Mapping(target = "productDescription", source = "product.productDescription")
    @Mapping(source = "seller.nickname", target = "sellerNickname")
    @Mapping(source = "product.images", target = "images")
    EndedProductDetailsDto toEndedProductDetailsDto(Auction auction);

    default List<String> filterRepresentativeImages(List<AuctionProductImage> images) {
        return images.stream()
                .map(AuctionProductImage::getImageUrl)
                .collect(Collectors.toList());
    }

}
