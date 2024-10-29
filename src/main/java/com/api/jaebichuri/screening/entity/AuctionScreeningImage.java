package com.api.jaebichuri.screening.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AuctionScreeningImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String imageUrl;

    @Column(nullable = false)
    private Boolean isRepresentative;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "auction_screening_id", nullable = false)
    private AuctionScreening auctionScreening;

    public void setAuctionScreening(AuctionScreening auctionScreening) {
        this.auctionScreening = auctionScreening;
    }

}
