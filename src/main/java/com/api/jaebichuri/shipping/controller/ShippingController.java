package com.api.jaebichuri.shipping.controller;

import com.api.jaebichuri.global.response.ApiResponse;
import com.api.jaebichuri.member.entity.Member;
import com.api.jaebichuri.shipping.dto.shippingDetailsDto;
import com.api.jaebichuri.shipping.dto.ShippingStatusCountDto;
import com.api.jaebichuri.shipping.service.ShippingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/shipping")
@Tag(name = "Shipping", description = "배송 조회 관련 API")
public class ShippingController {

    private final ShippingService shippingService;

    @Operation(
            summary = "배송 상태별 물품 개수 조회 API",
            description = "각 배송 상태에 해당하는 물품의 개수를 조회하는 API입니다. 해당 API는 사용자 인증이 요구됩니다." +
                    "물품 개수를 조회할 때 해당 API를 사용하셔도 되고, 배송 중인 물품 정보 조회 API에서 각 항목 개수를 직접 계산해서 사용하셔도 됩니다.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "COMMON200",
                            description = "배송 상태별 물품 개수 조회 결과",
                            content = @Content(schema = @Schema(implementation = ShippingStatusCountDto.class))
                    )
            }
    )
    @GetMapping("/count")
    public ResponseEntity<ApiResponse<ShippingStatusCountDto>> getShippingStatusCount(@AuthenticationPrincipal Member member) {
        return ResponseEntity.ok(ApiResponse.onSuccess(shippingService.getShippingStatusCount(member)));
    }

    @Operation(
            summary = "배송 중인 물품 정보 조회 API",
            description = "배송 중인 물품에 대한 정보를 조회하는 API입니다. 해당 API는 사용자 인증이 요구됩니다.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "COMMON200",
                            description = "배송 중인 물품 정보 조회 결과",
                            content = @Content(schema = @Schema(implementation = shippingDetailsDto.class))
                    )
            }
    )
    @GetMapping("/details")
    public ResponseEntity<ApiResponse<List<shippingDetailsDto>>> getShippingDetails(@AuthenticationPrincipal Member member) {
        return ResponseEntity.ok(ApiResponse.onSuccess(shippingService.getShippingDetails(member)));
    }

}
