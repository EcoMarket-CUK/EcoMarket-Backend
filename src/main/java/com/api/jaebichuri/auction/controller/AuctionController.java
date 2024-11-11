package com.api.jaebichuri.auction.controller;

import com.api.jaebichuri.auction.dto.OngoingAuctionProductDto;
import com.api.jaebichuri.auction.dto.UpcomingAuctionProductDto;
import com.api.jaebichuri.auction.enums.AuctionCategory;
import com.api.jaebichuri.auction.enums.AuctionStatus;
import com.api.jaebichuri.auction.service.AuctionService;
import com.api.jaebichuri.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auctions")
@Tag(name = "Auction", description = "경매 목록 조회 관련 API")
public class AuctionController {

    private final AuctionService auctionService;

    @Operation(
            summary = "경매 Top5 목록 조회 API",
            description = "가장 최근 생성된 경매 5개를 반환하는 API입니다. 진행 중인, 진행 예정인 경매 모두 해당 API를 사용하면 됩니다." +
                          "둘의 responseCode가 같아 진행 중인 경매 목록 조회 결과는 COMMON200/ONGOING, 진행 예정인 경매 목록 조회 결과는 COMMON200/UPCOMING로 표기했습니다.",
            parameters = {
                    @Parameter(name = "status", description = "경매 상태", required = true),
                    @Parameter(name = "category", description = "경매 카테고리", required = false)
            },
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "COMMON200/ONGOING",
                            description = "진행 중인 경매 Top5 목록 조회 결과",
                            content = @Content(schema = @Schema(implementation = OngoingAuctionProductDto.class))
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "COMMON200/UPCOMING",
                            description = "진행 예정인 경매 Top5 목록 조회 결과",
                            content = @Content(schema = @Schema(implementation = UpcomingAuctionProductDto.class))
                    )
            }
    )
    @GetMapping
    public ResponseEntity<ApiResponse<List<?>>> getTopAuctionsByStatus(@RequestParam AuctionStatus status,
                                                                       @RequestParam(required = false) AuctionCategory category) {
        return ResponseEntity.ok(ApiResponse.onSuccess(auctionService.getTopAuctionsByStatus(status, category)));
    }

    @Operation(
            summary = "진행 중인 경매 전체 목록 조회 API",
            description = "진행 중인 경매 전체를 반환하는 API입니다. 현재 정렬 기준은 최신순입니다.",
            parameters = {
                    @Parameter(name = "category", description = "경매 카테고리", required = false)
            },
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "COMMON200",
                            description = "진행 중인 경매 전체 목록 조회 결과",
                            content = @Content(schema = @Schema(implementation = OngoingAuctionProductDto.class))
                    )
            }
    )
    @GetMapping("/ongoing")
    public ResponseEntity<ApiResponse<List<OngoingAuctionProductDto>>> getAllOngoingAuctions(@RequestParam(required = false) AuctionCategory category) {
        return ResponseEntity.ok(ApiResponse.onSuccess(auctionService.getAllOngoingAuctions(category)));
    }

    @Operation(
            summary = "검색어로 경매 검색 API",
            description = "입력된 검색어를 포함하는 상품명을 갖는 경매를 반환하는 API입니다. 현재 정렬 기준은 최신순입니다.",
            parameters = {
                    @Parameter(name = "keyword", description = "검색어", required = true)
            },
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "COMMON200",
                            description = "검색어 검색 결과",
                            content = @Content(schema = @Schema(implementation = OngoingAuctionProductDto.class))
                    )
            }
    )
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<?>>> searchOngoingAuctions(@RequestParam String keyword) {
        return ResponseEntity.ok(ApiResponse.onSuccess(auctionService.searchOngoingAuctions(keyword)));
    }

}

