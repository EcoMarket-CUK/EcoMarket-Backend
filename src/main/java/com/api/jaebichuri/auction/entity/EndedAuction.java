package com.api.jaebichuri.auction.entity;

import com.api.jaebichuri.auction.enums.AuctionCategory;
import com.api.jaebichuri.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EndedAuction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false)
    private Long finalBidPrice;

    @Column(nullable = false)
    private Boolean isBidSuccessful;

    @Enumerated(EnumType.STRING)
    private AuctionCategory auctionCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member seller;

}
