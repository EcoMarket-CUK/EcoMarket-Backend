package com.api.jaebichuri.screening.dto;

import com.api.jaebichuri.auction.enums.AuctionCategory;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class ScreeningDto {

    private String productName;

    private String productDescription;

    private Long desiredStartPrice;

    private AuctionCategory auctionCategory;

}
