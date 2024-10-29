package com.api.jaebichuri.product.repository;

import com.api.jaebichuri.product.entity.AuctionProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<AuctionProduct, Long> {

}
