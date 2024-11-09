package com.api.jaebichuri.bid.repository;

import com.api.jaebichuri.auction.entity.Auction;
import com.api.jaebichuri.bid.entity.AuctionBid;
import com.api.jaebichuri.member.entity.Member;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AuctionBidRepository extends JpaRepository<AuctionBid, Long> {

    Optional<AuctionBid> findTopByAuctionOrderByBidPriceDesc(Auction auction);

    boolean existsByBidderAndAuction(Member member, Auction auction);

    List<AuctionBid> findTop3ByAuctionOrderByBidPriceDesc(Auction auction);

    List<AuctionBid> findAllByAuction(Auction auction);

    @Query("SELECT COUNT(ab) FROM AuctionBid ab WHERE ab.auction = :auction AND ab.createdAt >= :startOfDay AND ab.createdAt < :endOfDay")
    Long countByAuctionAndCreatedAtBetween(@Param("auction") Auction auction,
        @Param("startOfDay") LocalDateTime startOfDay, @Param("endOfDay") LocalDateTime endOfDay);
}
