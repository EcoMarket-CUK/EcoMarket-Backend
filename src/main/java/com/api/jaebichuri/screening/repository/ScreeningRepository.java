package com.api.jaebichuri.auction.repository;

import com.api.jaebichuri.auction.entity.AuctionScreening;
import com.api.jaebichuri.auction.enums.AuctionScreeningStatus;
import com.api.jaebichuri.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScreeningRepository extends JpaRepository<AuctionScreening, Long> {

    List<AuctionScreening> findBySellerAndScreeningStatus(Member member, AuctionScreeningStatus auctionScreeningStatus);

}
