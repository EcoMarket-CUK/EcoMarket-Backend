package com.api.jaebichuri.auction.entity;

import jakarta.persistence.*;
import lombok.*;

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

}
