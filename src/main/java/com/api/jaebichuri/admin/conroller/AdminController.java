package com.api.jaebichuri.admin.conroller;

import com.api.jaebichuri.admin.service.AdminService;
import com.api.jaebichuri.global.response.ApiResponse;
import com.api.jaebichuri.member.entity.Member;
import com.api.jaebichuri.member.enums.Role;
import com.api.jaebichuri.screening.enums.AuctionScreeningStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
@Tag(name = "Admin", description = "관리자 관련 API")
public class AdminController {

    private final AdminService adminService;

    @Operation(
            summary = "사용자 권한 변경 API",
            description = "사용자 권한을 User에서 Admin으로 변경하는 API입니다. admin 테스트를 위한 API이며, 실제 연동은 이뤄지지 않습니다. 해당 API는 사용자 인증이 요구됩니다.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "COMMON200",
                            description = "사용자 권한 변경 결과",
                            content = @Content(schema = @Schema(implementation = String.class))
                    )
            }
    )
    @PutMapping("/role/{role}")
    public ResponseEntity<ApiResponse<String>> changeRole(@PathVariable Role role,
                                                          @AuthenticationPrincipal Member member) {
        return ResponseEntity.ok(ApiResponse.onSuccess(adminService.changeRole(member, role)));
    }

    @Operation(
            summary = "심사 상태 변경 API",
            description = "심사 상태를 변경하는 API입니다. 해당 API는 사용자 인증이 요구되며, admin 권한을 가진 사용자만 접근 가능합니다.",
            parameters = {
                    @Parameter(name = "screeningId", description = "심사 중인 경매 식별자 ID", required = true),
                    @Parameter(name = "newStatus", description = "변경할 새로운 심사 상태", required = true)
            },
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "COMMON200",
                            description = "심사 상태 변경 결과",
                            content = @Content(schema = @Schema(implementation = String.class))
                    )
            }
    )
    @PutMapping("/{screeningId}/status")
    public ResponseEntity<ApiResponse<String>> updateScreeningStatus(@PathVariable Long screeningId,
                                                                     @RequestParam AuctionScreeningStatus newStatus) {
        return ResponseEntity.ok(ApiResponse.onSuccess(adminService.updateScreeningStatus(screeningId, newStatus)));
    }

}