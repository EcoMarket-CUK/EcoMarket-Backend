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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public String changeRole(Member member, Role newRole) {
        if (member.getRole() == newRole) {
            return "Member is already a " + newRole + ".";
        }

        member.setRole(newRole);
        memberRepository.save(member);

        return "Member successfully promoted to " + newRole + ".";
    }

    @Transactional
    public String updateScreeningStatus(Long screeningId, AuctionScreeningStatus newStatus) {
        AuctionScreening screening = screeningRepository.findById(screeningId)
                .orElseThrow(() -> new CustomException(ErrorStatus._SCREENING_NOT_FOUND));

        screening.setScreeningStatus(newStatus);
        screeningRepository.save(screening);

        if(newStatus == AuctionScreeningStatus.INSPECTION_COMPLETED) {
            moveToAuction(screening);
        }

        return "Auction screening status has been updated.";
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

}
