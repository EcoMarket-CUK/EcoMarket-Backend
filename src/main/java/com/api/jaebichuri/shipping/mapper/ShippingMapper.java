package com.api.jaebichuri.shipping.mapper;

import com.api.jaebichuri.product.entity.AuctionProductImage;
import com.api.jaebichuri.shipping.dto.shippingDetailsDto;
import com.api.jaebichuri.shipping.entity.Shipping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ShippingMapper {

    ShippingMapper INSTANCE = Mappers.getMapper(ShippingMapper.class);

    @Mapping(source = "auction.product.productName", target = "productName")
    @Mapping(source = "auction.product.productDescription", target = "productDescription")
    @Mapping(source = "auction.auctionCategory", target = "auctionCategory")
    @Mapping(source = "auction.finalBidPrice", target = "finalBidPrice")
    @Mapping(target = "imageUrl", expression = "java(getRepresentativeImageUrl(shipping.getAuction().getProduct().getImages()))")
    shippingDetailsDto toShippingDetailsDto(Shipping shipping);

    List<shippingDetailsDto> toShippingDetailsDtoList(List<Shipping> shippings);

    default String getRepresentativeImageUrl(List<AuctionProductImage> images) {
        return images.stream()
                .filter(AuctionProductImage::getIsRepresentative)
                .map(AuctionProductImage::getImageUrl)
                .findFirst()
                .orElse(null);
    }

}
