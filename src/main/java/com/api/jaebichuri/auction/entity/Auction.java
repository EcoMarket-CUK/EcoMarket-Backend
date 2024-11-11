package com.api.jaebichuri.auction.entity;

import com.api.jaebichuri.auction.enums.AuctionCategory;
import com.api.jaebichuri.auction.enums.AuctionStatus;
import com.api.jaebichuri.bid.entity.AuctionBid;
import com.api.jaebichuri.global.entity.BaseEntity;
import com.api.jaebichuri.member.entity.Member;
import com.api.jaebichuri.product.entity.AuctionProduct;
import com.api.jaebichuri.screening.entity.AuctionScreening;
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
@Table(indexes = @Index(name = "idx_auction_status", columnList = "auctionStatus"))
public class Auction extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long startPrice;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = false)
    private LocalDateTime endTime;

    @Column(nullable = true)
    private Long currentPrice;

    // 입찰자 수
    @Column(nullable = true)
    private Long numOfBidders = 0L;

    @Column(nullable = true)
    private Long finalBidPrice;

    @Column(nullable = true)
    private Boolean isBidSuccessful;

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

    public Auction updateNumOfBidders() {
        this.numOfBidders++;
        return this;
    }

    public void startAuction() {
        this.auctionStatus = AuctionStatus.ONGOING;
    }

    public void endAuction() {
        this.auctionStatus = AuctionStatus.ENDED;
        this.isBidSuccessful = (this.finalBidPrice != null);
    }

    public static Auction fromScreening(AuctionScreening screening, AuctionProduct product) {
        return Auction.builder()
                .startPrice(screening.getDesiredStartPrice())
                .startTime(screening.getStartTime())
                .endTime(screening.getEndTime())
                .auctionStatus(AuctionStatus.UPCOMING)
                .auctionCategory(screening.getAuctionCategory())
                .seller(screening.getSeller())
                .product(product)
                .build();
    }

}
