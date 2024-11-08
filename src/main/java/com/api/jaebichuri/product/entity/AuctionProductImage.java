package com.api.jaebichuri.product.entity;

import com.api.jaebichuri.screening.entity.AuctionScreeningImage;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AuctionProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String imageUrl;

    @Column(nullable = false)
    private Boolean isRepresentative;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "auction_product_id", nullable = false)
    private AuctionProduct auctionProduct;

    public static AuctionProductImage fromScreeningImage(AuctionScreeningImage image, AuctionProduct product) {
        return AuctionProductImage.builder()
                .imageUrl(image.getImageUrl())
                .isRepresentative(image.getIsRepresentative())
                .auctionProduct(product)
                .build();
    }

}
