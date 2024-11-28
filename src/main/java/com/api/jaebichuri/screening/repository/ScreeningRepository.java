package com.api.jaebichuri.screening.repository;

import com.api.jaebichuri.member.entity.Member;
import com.api.jaebichuri.screening.entity.AuctionScreening;
import com.api.jaebichuri.screening.enums.AuctionScreeningStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScreeningRepository extends JpaRepository<AuctionScreening, Long> {

    List<AuctionScreening> findAllBySeller(Member member);

    boolean existsBySellerAndScreeningStatusNot(Member member,
        AuctionScreeningStatus auctionScreeningStatus);

}
