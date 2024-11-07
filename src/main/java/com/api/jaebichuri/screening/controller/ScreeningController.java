package com.api.jaebichuri.screening.controller;

import com.api.jaebichuri.auction.dto.OngoingAuctionProductDto;
import com.api.jaebichuri.auction.enums.AuctionStatus;
import com.api.jaebichuri.global.response.ApiResponse;
import com.api.jaebichuri.member.entity.Member;
import com.api.jaebichuri.screening.dto.EndedAuctionProductDto;
import com.api.jaebichuri.screening.dto.ScreeningDto;
import com.api.jaebichuri.screening.dto.ScreeningListDto;
import com.api.jaebichuri.screening.service.ScreeningService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/screenings")
@Tag(name = "Screening", description = "경매 심사 관련 API")
public class ScreeningController {

    private final ScreeningService screeningService;

    @Operation(
            summary = "경매 상품 등록 API",
            description = "사용자가 경매 상품을 등록하기 위한 API입니다. 해당 API는 사용자 인증이 요구됩니다.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "COMMON200",
                            description = "경매 상품 등록 결과 (Auction screening has been created)",
                            content = @Content(schema = @Schema(implementation = String.class))
                    )
            }
    )
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<String>> createAuctionScreening(@Parameter(name = "screeningDto", description = "경매 상품 등록 정보 DTO", required = true)
                                                                      @Valid @RequestPart ScreeningDto screeningDto,

                                                                      @Parameter(name = "images", description = "상품 이미지 파일", required = true)
                                                                      @RequestPart List<MultipartFile> images,

                                                                      @AuthenticationPrincipal Member member) throws IOException {
        return ResponseEntity.ok(ApiResponse.onSuccess(screeningService.createAuctionScreening(screeningDto, images, member)));
    }

    @Operation(
            summary = "심사 중인 경매 상품 목록 조회 API",
            description = "사용자가 등록한 경매 상품 중 심사 중인 경매를 반환하는 API입니다. 해당 API는 사용자 인증이 요구됩니다.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "COMMON200",
                            description = "심사 중인 경매 목록 조회 결과",
                            content = @Content(schema = @Schema(implementation = ScreeningListDto.class))
                    )
            }
    )
    @GetMapping
    public ResponseEntity<ApiResponse<List<ScreeningListDto>>> getScreenings(@AuthenticationPrincipal Member member) {
        return ResponseEntity.ok(ApiResponse.onSuccess(screeningService.getScreenings(member)));
    }

    @Operation(
            summary = "진행 중인, 마감된 경매 목록 조회 API",
            description = "사용자가 등록한 경매 상품 중 진행 중이거나 마감된 경매를 반환하는 API입니다. 진행 중인, 마감된 경매 모두 해당 API를 사용하면 됩니다. 해당 API는 사용자 인증이 요구됩니다.",
            parameters = {
                    @Parameter(name = "status", description = "경매 상태", required = true)
            },
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "COMMON200/ONGOING",
                            description = "진행 중인 경매 목록 조회 결과",
                            content = @Content(schema = @Schema(implementation = OngoingAuctionProductDto.class))
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "COMMON200/ENDED",
                            description = "마감된 경매 목록 조회 결과",
                            content = @Content(schema = @Schema(implementation = EndedAuctionProductDto.class))
                    )
            }
    )
    @GetMapping("/member-auctions")
    public ResponseEntity<ApiResponse<List<?>>> getAuctionsByStatus(@RequestParam AuctionStatus status,
                                                                    @AuthenticationPrincipal Member member) {
        return ResponseEntity.ok(ApiResponse.onSuccess(screeningService.getAuctionsByStatus(status, member)));
    }

}

