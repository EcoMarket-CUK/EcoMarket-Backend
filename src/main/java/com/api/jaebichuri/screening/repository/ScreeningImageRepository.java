package com.api.jaebichuri.screening.repository;

import com.api.jaebichuri.screening.entity.AuctionScreeningImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScreeningImageRepository extends JpaRepository<AuctionScreeningImage, Long> {

}
