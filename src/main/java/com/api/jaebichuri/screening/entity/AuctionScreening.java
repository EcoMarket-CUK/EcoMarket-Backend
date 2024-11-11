package com.api.jaebichuri.screening.entity;

import com.api.jaebichuri.auction.enums.AuctionCategory;
import com.api.jaebichuri.global.entity.BaseEntity;
import com.api.jaebichuri.member.entity.Member;
import com.api.jaebichuri.screening.enums.AuctionScreeningStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class AuctionScreening extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String productName;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String productDescription;

    @Column(nullable = false)
    private Long desiredStartPrice;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = false)
    private LocalDateTime endTime;

    @Enumerated(EnumType.STRING)
    private AuctionScreeningStatus screeningStatus;

    @Enumerated(EnumType.STRING)
    private AuctionCategory auctionCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member seller;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AuctionScreeningImage> images = new ArrayList<>();

    public void updateSeller(Member seller) {
            this.seller = seller;
        }

    public void updateImages(List<AuctionScreeningImage> images) {
        this.images = images;
    }

    public void updateScreeningStatus(AuctionScreeningStatus screeningStatus) {
        this.screeningStatus = screeningStatus;
    }

}
