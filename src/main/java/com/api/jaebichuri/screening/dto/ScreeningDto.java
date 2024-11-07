package com.api.jaebichuri.screening.dto;

import com.api.jaebichuri.auction.enums.AuctionCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@Schema(description = "경매 상품 등록 정보 DTO")
public class ScreeningDto {

    @Schema(description = "상품명", example = "모자")
    private String productName;

    @Schema(description = "상품명", example = "모자에 대한 설명입니다.")
    private String productDescription;

    @Schema(description = "희망 입찰 시작가", example = "20000")
    private Long desiredStartPrice;

    @Schema(description = "경매 카테고리", example = "CLOTHING")
    private AuctionCategory auctionCategory;

}

