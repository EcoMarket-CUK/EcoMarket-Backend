package com.api.jaebichuri.product.entity;

import com.api.jaebichuri.auction.entity.Auction;
import com.api.jaebichuri.screening.entity.AuctionScreening;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AuctionProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String productName;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String productDescription;

    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL)
    private Auction auction;

    @OneToMany(mappedBy = "auctionProduct", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AuctionProductImage> images = new ArrayList<>();

    public List<AuctionProductImage> getImages() {
        return images;
    }

    public static AuctionProduct fromScreening(AuctionScreening screening) {
        return AuctionProduct.builder()
                .productName(screening.getProductName())
                .productDescription(screening.getProductDescription())
                .build();
    }

}
