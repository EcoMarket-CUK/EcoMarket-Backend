package com.api.jaebichuri.auction.repository;

import com.api.jaebichuri.auction.entity.Auction;
import com.api.jaebichuri.auction.enums.AuctionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuctionRepository extends JpaRepository<Auction, Long> {

    Optional<Auction> findByIdAndAuctionStatus(long auctionId, AuctionStatus auctionStatus);

}
