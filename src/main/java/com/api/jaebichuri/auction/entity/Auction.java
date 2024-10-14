package com.api.jaebichuri.auction.entity;

import com.api.jaebichuri.auction.enums.AuctionCategory;
import com.api.jaebichuri.auction.enums.AuctionStatus;
import com.api.jaebichuri.auctionbid.entity.AuctionBid;
import com.api.jaebichuri.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Auction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long startPrice;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = false)
    private LocalDateTime endTime;

    @Enumerated(EnumType.STRING)
    private AuctionStatus auctionStatus;

    @Enumerated(EnumType.STRING)
    private AuctionCategory auctionCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member seller;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "auction_product_id", nullable = false)
    private AuctionProduct product;

    @OneToMany(mappedBy = "auction", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AuctionBid> bids = new ArrayList<>();

}
