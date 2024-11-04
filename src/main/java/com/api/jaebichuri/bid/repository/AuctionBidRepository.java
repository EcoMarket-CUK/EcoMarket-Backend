package com.api.jaebichuri.bid.repository;

import com.api.jaebichuri.auction.entity.Auction;
import com.api.jaebichuri.bid.entity.AuctionBid;
import com.api.jaebichuri.member.entity.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuctionBidRepository extends JpaRepository<AuctionBid, Long> {

    Optional<AuctionBid> findTopByAuctionOrderByBidPriceDesc(Auction auction);

    boolean existsByBidderAndAuction(Member member, Auction auction);
}
