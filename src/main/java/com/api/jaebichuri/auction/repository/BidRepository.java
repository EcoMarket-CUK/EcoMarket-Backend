package com.api.jaebichuri.auction.repository;

import com.api.jaebichuri.auction.entity.AuctionBid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BidRepository extends JpaRepository<AuctionBid, Long> {

}
