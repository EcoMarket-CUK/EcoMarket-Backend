package com.api.jaebichuri.product.repository;

import com.api.jaebichuri.product.entity.AuctionProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductImageRepository extends JpaRepository<AuctionProductImage, Long> {

}
