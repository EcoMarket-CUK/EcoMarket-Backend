package com.api.jaebichuri.shipping.entity;

import com.api.jaebichuri.auction.entity.Auction;
import com.api.jaebichuri.shipping.enums.ShippingStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Shipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ShippingStatus shippingStatus;

    @Column(nullable = true)
    private String shippingCompany;

    @Column(nullable = true)
    private String trackingNumber;

    @Column(nullable = true)
    private LocalDateTime shippingDate;

    @Column(nullable = true)
    private LocalDateTime deliveryDate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "auction_id", nullable = false)
    private Auction auction;

    public void updateShippingStatus(ShippingStatus newShippingStatus) {
        this.shippingStatus = newShippingStatus;
    }

    public void updateShippingCompany(String newShippingCompany) {
        this.shippingCompany = newShippingCompany;
    }

    public void updateTrackingNumber(String newTrackingNumber) {
        this.trackingNumber = newTrackingNumber;
    }

    public void updateShippingDate(LocalDateTime newShippingDate) {
        this.shippingDate = newShippingDate;
    }

    public void updateDeliveryDate(LocalDateTime newDeliveryDate) {
        this.deliveryDate = newDeliveryDate;
    }

}
