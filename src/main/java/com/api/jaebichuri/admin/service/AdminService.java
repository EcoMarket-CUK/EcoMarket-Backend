package com.api.jaebichuri.admin.service;

import com.api.jaebichuri.auction.entity.Auction;
import com.api.jaebichuri.auction.repository.AuctionRepository;
import com.api.jaebichuri.global.response.code.status.ErrorStatus;
import com.api.jaebichuri.global.response.exception.CustomException;
import com.api.jaebichuri.member.entity.Member;
import com.api.jaebichuri.member.enums.Role;
import com.api.jaebichuri.member.repository.MemberRepository;
import com.api.jaebichuri.product.entity.AuctionProduct;
import com.api.jaebichuri.product.entity.AuctionProductImage;
import com.api.jaebichuri.product.repository.ProductImageRepository;
import com.api.jaebichuri.product.repository.ProductRepository;
import com.api.jaebichuri.screening.entity.AuctionScreening;
import com.api.jaebichuri.screening.enums.AuctionScreeningStatus;
import com.api.jaebichuri.screening.repository.ScreeningRepository;
import com.api.jaebichuri.shipping.entity.Shipment;
import com.api.jaebichuri.shipping.enums.ShippingStatus;
import com.api.jaebichuri.shipping.repository.ShippingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final ScreeningRepository screeningRepository;
    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;
    private final AuctionRepository auctionRepository;
    private final MemberRepository memberRepository;
    private final ShippingRepository shippingRepository;

    @Transactional
    public String changeRole(Member member, Role newRole) {
        if (member.getRole() == newRole) {
            return "Member is already a " + newRole + ".";
        }

        member.updateRole(newRole);
        memberRepository.save(member);

        return "Member successfully promoted to " + newRole + ".";
    }

    @Transactional
    public String updateScreeningStatus(Long screeningId, AuctionScreeningStatus newStatus) {
        AuctionScreening screening = screeningRepository.findById(screeningId)
                .orElseThrow(() -> new CustomException(ErrorStatus._SCREENING_NOT_FOUND));

        screening.updateScreeningStatus(newStatus);
        screeningRepository.save(screening);

        if(newStatus == AuctionScreeningStatus.INSPECTION_COMPLETED) {
            moveToAuction(screening);
        }

        return "Auction screening status has been updated.";
    }

    @Transactional
    public String updateShipmentStatus(Long auctionId, ShippingStatus newStatus, String shippingCompany, String trackingNumber) {
        Auction auction = auctionRepository.findById(auctionId)
                .orElseThrow(() -> new CustomException(ErrorStatus._AUCTION_NOT_FOUND));

        handleStatusSpecificUpdates(auction, newStatus, shippingCompany, trackingNumber);

        return "Shipment status updated successfully.";
    }

    private void moveToAuction(AuctionScreening screening) {
        AuctionProduct product = AuctionProduct.fromScreening(screening);
        productRepository.save(product);

        List<AuctionProductImage> productImages = screening.getImages().stream()
                .map(screeningImage -> AuctionProductImage.fromScreeningImage(screeningImage, product))
                .collect(Collectors.toList());
        productImageRepository.saveAll(productImages);

        Auction auction = Auction.fromScreening(screening, product);
        auctionRepository.save(auction);
    }

    private Shipment findOrCreateShipment(Auction auction, ShippingStatus newStatus) {
        return shippingRepository.findByAuction(auction)
                .orElseGet(() -> {
                    if (newStatus == ShippingStatus.PAYMENT_CONFIRMED) {
                        Shipment newShipment = Shipment.builder()
                                .auction(auction)
                                .shippingStatus(newStatus)
                                .build();

                        shippingRepository.save(newShipment);
                        return newShipment;
                    } else {
                        throw new CustomException(ErrorStatus._SHIPPING_NOT_FOUND);
                    }
                });
    }

    private void handleStatusSpecificUpdates(Auction auction, ShippingStatus newStatus, String shippingCompany, String trackingNumber) {
        Shipment shipment = findOrCreateShipment(auction, newStatus);

        shipment.updateShippingStatus(newStatus);

        if (newStatus == ShippingStatus.SHIPPING) {
            shipment.updateShippingCompany(shippingCompany);
            shipment.updateTrackingNumber(trackingNumber);
            shipment.updateShippingDate(LocalDateTime.now());
        } else if (newStatus == ShippingStatus.DELIVERED) {
            shipment.updateDeliveryDate(LocalDateTime.now());
        }

        shippingRepository.save(shipment);
    }

}
