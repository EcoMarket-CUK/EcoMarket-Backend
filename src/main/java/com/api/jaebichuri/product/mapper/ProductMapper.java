package com.api.jaebichuri.product.mapper;

import com.api.jaebichuri.bid.dto.AuctionBidHttpResponse.BidDatePriceResponse;
import com.api.jaebichuri.bid.dto.AuctionBidHttpResponse.BidVolumeResponse;
import com.api.jaebichuri.product.dto.EndedProductDetailsDto;
import com.api.jaebichuri.product.dto.OngoingProductDetailsDto;
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

    @Mapping(source = "memberId", target = "memberId")
    @Mapping(source = "auction.product.id", target = "productId")
    @Mapping(source = "auction.id", target = "auctionId")
    @Mapping(source = "auction.auctionCategory", target = "auctionCategory")
    @Mapping(source = "auction.product.images", target = "images")
    @Mapping(source = "auction.product.productName", target = "productName")
    @Mapping(source = "auction.product.productDescription", target = "productDescription")
    @Mapping(source = "sellerNickname", target = "sellerNickname")
    @Mapping(source = "auction.endTime", target = "endTime")
    @Mapping(source = "auction.numOfBidders", target = "numOfBidders")
    @Mapping(source = "top3BidDatePrice", target = "top3BidDatePrice")
    @Mapping(source = "bidVolumeByDate", target = "bidVolumeByDate")
    OngoingProductDetailsDto toOngoingProductDetailsDto(Auction auction, String sellerNickname,
        List<BidDatePriceResponse> top3BidDatePrice, List<BidVolumeResponse> bidVolumeByDate, Long memberId);

    default List<String> filterRepresentativeImages(List<AuctionProductImage> images) {
        return images.stream()
            .map(AuctionProductImage::getImageUrl)
            .collect(Collectors.toList());
    }

}
