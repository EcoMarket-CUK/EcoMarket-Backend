package com.api.jaebichuri.auction.repository;

import com.api.jaebichuri.auction.entity.AuctionProduct;
import com.api.jaebichuri.auction.enums.AuctionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<AuctionProduct, Long> {

}
