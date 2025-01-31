package com.api.jaebichuri.auction.repository;

import com.api.jaebichuri.auction.entity.Auction;
import com.api.jaebichuri.auction.enums.AuctionCategory;
import com.api.jaebichuri.auction.enums.AuctionStatus;
import com.api.jaebichuri.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AuctionRepository extends JpaRepository<Auction, Long> {

    Optional<Auction> findByIdAndAuctionStatus(long auctionId, AuctionStatus auctionStatus);

    List<Auction> findTop5ByAuctionStatusOrderByCreatedAtDesc(AuctionStatus auctionStatus);

    List<Auction> findTop5ByAuctionStatusAndAuctionCategoryOrderByCreatedAtDesc(AuctionStatus auctionStatus, AuctionCategory category);

    List<Auction> findAllByAuctionStatusOrderByCreatedAtDesc(AuctionStatus auctionStatus);

    List<Auction> findAllByAuctionStatusAndAuctionCategoryOrderByCreatedAtDesc(AuctionStatus auctionStatus, AuctionCategory category);

    List<Auction> findBySellerAndAuctionStatus(Member member, AuctionStatus auctionStatus);

    List<Auction> findByProduct_ProductNameContainingAndAuctionStatusOrderByCreatedAtDesc(String keyword, AuctionStatus status);

    List<Auction> findByAuctionStatusAndStartTimeLessThanEqual(AuctionStatus auctionStatus, LocalDateTime time);

    List<Auction> findByAuctionStatusAndEndTimeLessThanEqual(AuctionStatus auctionStatus, LocalDateTime time);

    Optional<Auction> findByIdAndSeller(Long auctionId, Member member);

    boolean existsBySellerAndAuctionStatusNot(Member member, AuctionStatus auctionStatus);

    List<Auction> findAllByAuctionStatusNot(AuctionStatus auctionStatus);

    List<Auction> findAllByFinalBidder(Member member);
}