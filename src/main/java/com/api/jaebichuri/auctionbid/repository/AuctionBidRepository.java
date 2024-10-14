package com.api.jaebichuri.auctionbid.repository;

import com.api.jaebichuri.auctionbid.entity.AuctionBid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuctionBidRepository extends JpaRepository<AuctionBid, Long> {

}
