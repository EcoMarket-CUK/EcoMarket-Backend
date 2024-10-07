package com.api.jaebichuri.auction.repository;

import com.api.jaebichuri.auction.entity.EndedAuction;
import com.api.jaebichuri.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EndedAuctionRepository extends JpaRepository<EndedAuction, Long> {

    List<EndedAuction> findBySeller(Member member);

}
