package com.api.jaebichuri.shipping.repository;

import com.api.jaebichuri.auction.entity.Auction;
import com.api.jaebichuri.member.entity.Member;
import com.api.jaebichuri.shipping.entity.Shipment;
import com.api.jaebichuri.shipping.enums.ShippingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShippingRepository extends JpaRepository<Shipment, Long> {

    Optional<Shipment> findByAuction(Auction auction);

    int countByShippingStatusAndAuction_Seller(ShippingStatus shippingStatus, Member member);

    List<Shipment> findByAuction_Seller(Member member);

}