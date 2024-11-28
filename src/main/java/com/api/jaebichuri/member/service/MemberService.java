package com.api.jaebichuri.member.service;

import com.api.jaebichuri.auction.enums.AuctionStatus;
import com.api.jaebichuri.auction.repository.AuctionRepository;
import com.api.jaebichuri.auth.dto.LoginSuccessDto;
import com.api.jaebichuri.auth.dto.TokenResponseDto;
import com.api.jaebichuri.auth.repository.RefreshTokenRepository;
import com.api.jaebichuri.bid.repository.AuctionBidRepository;
import com.api.jaebichuri.global.response.code.status.ErrorStatus;
import com.api.jaebichuri.global.response.exception.CustomException;
import com.api.jaebichuri.member.dto.MemberInfoRequestDto;
import com.api.jaebichuri.member.dto.MemberInfoResponseDTO;
import com.api.jaebichuri.member.entity.Member;
import com.api.jaebichuri.member.enums.Role;
import com.api.jaebichuri.member.mapper.MemberMapper;
import com.api.jaebichuri.member.repository.MemberRepository;
import com.api.jaebichuri.auth.jwt.JwtUtil;
import com.api.jaebichuri.screening.enums.AuctionScreeningStatus;
import com.api.jaebichuri.screening.repository.ScreeningRepository;
import com.api.jaebichuri.shipping.enums.ShippingStatus;
import com.api.jaebichuri.shipping.repository.ShippingRepository;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private static final String JSON_ATTRIBUTE_NAME_NICKNAME = "nickname";
    private static final String JSON_ATTRIBUTE_NAME_ID = "id";
    private static final String JSON_ATTRIBUTE_NAME_PROFILE_IMAGE = "profile_image";

    private final RefreshTokenRepository refreshTokenRepository;
    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;
    private final MemberMapper memberMapper;
    private final AuctionRepository auctionRepository;
    private final ShippingRepository shippingRepository;
    private final AuctionBidRepository auctionBidRepository;
    private final ScreeningRepository screeningRepository;

    public LoginSuccessDto login(Map<String, String> memberInfo) {
        String clientId = memberInfo.get(JSON_ATTRIBUTE_NAME_ID);
        String kakaoProfileNickname = memberInfo.get(JSON_ATTRIBUTE_NAME_NICKNAME);
        String kakaoProfileImage = memberInfo.get(JSON_ATTRIBUTE_NAME_PROFILE_IMAGE);

        String accessToken = jwtUtil.generateAccessToken(clientId);
        String refreshToken = jwtUtil.generateRefreshToken(clientId);

        TokenResponseDto tokenResponseDto = TokenResponseDto.builder()
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .build();

        //redis에 refresh token 저장
        //key: clientId, value: refresh token
        refreshTokenRepository.save(clientId, refreshToken);

        Optional<Member> findMember = memberRepository.findByClientId(clientId);

        if (findMember.isPresent()) {
            // 최초 로그인이 아닌 사용자의 경우 카카오에서 사용하는 닉네임을 변경했을 가능성이 있기 때문에 로그인 시 업데이트 해준다.
            Member member = findMember.get();
            member.updateKakaoProfileNickname(kakaoProfileNickname);
            member.updateKakaoProfileImage(kakaoProfileImage);

            // 추가 정보 입력한 사용자일 경우
            if (member.getName() != null) {
                // jwt 엑세스 토큰 응답
                return LoginSuccessDto.builder()
                    .isFirstLogin(false)
                    .tokenResponseDto(tokenResponseDto)
                    .build();
            }

            // 소셜 로그인을 시도한 적이 있지만, 추가 정보 화면에서 나갔던 경우
            return LoginSuccessDto.builder()
                .isFirstLogin(true)
                .tokenResponseDto(tokenResponseDto)
                .build();
        }

        // 최초 로그인 하는 사용자의 경우 db에 사용자 정보를 저장한다.
        Member member = Member.builder()
            .clientId(clientId)
            .kakaoProfileNickname(kakaoProfileNickname)
            .kakaoProfileImage(kakaoProfileImage)
            .role(Role.USER)
            .build();

        memberRepository.save(member);

        // jwt 엑세스 토큰 응답
        return LoginSuccessDto.builder()
            .isFirstLogin(true)
            .tokenResponseDto(tokenResponseDto)
            .build();
    }

    public void saveMemberInfo(Member member, MemberInfoRequestDto requestDto) {
        Member findMember = memberRepository.findById(member.getId()).orElseThrow(
            () -> new CustomException(ErrorStatus._MEMBER_NOT_FOUND)
        );

        findMember.updateInfo(requestDto.getName(), requestDto.getNickname(),
            requestDto.getZipcode(), requestDto.getAddress());
    }

    public MemberInfoResponseDTO getMemberInfo(Member member) {
        return memberMapper.toMemberInfoResponseDTO(member);
    }

    public void withdraw(Member member) {
        // 탈퇴하려는 멤버의 Upcoming or Ongoing인 Auction이 존재하는지 조회
        if (auctionRepository.existsBySellerAndAuctionStatusNot(member, AuctionStatus.ENDED)) {
            throw new CustomException(ErrorStatus._WITHDRAW_CANNOT_CUZ_1);
        }

        // Ended인 Auction의 Shipping의 ShippingStatus가 DELIVERED가 아닌게 하나라도 있으면 탈퇴 불가
        shippingRepository.findByAuction_Seller(member).stream()
            .filter(shipping -> !ShippingStatus.DELIVERED.equals(shipping.getShippingStatus()))
            .findFirst()
            .ifPresent(shipping -> {
                throw new CustomException(ErrorStatus._WITHDRAW_CANNOT_CUZ_2);
            });

        // 멤버가 최고가로 입찰 중인 auction이 없는지 확인
        auctionRepository.findAllByAuctionStatusNot(AuctionStatus.UPCOMING).stream()
            .map(auction -> auctionBidRepository.findTopByAuctionOrderByBidPriceDesc(auction))
            .filter(Optional::isPresent)
            .map(Optional::get)
            .filter(auctionBid -> auctionBid.getBidder().getId().equals(member.getId()))
            .findFirst()
            .ifPresent(auctionBid -> {
                throw new CustomException(ErrorStatus._WITHDRAW_CANNOT_CUZ_3);
            });

        // 내가 입찰에 성공한 물품이 아직 배송 완료되지 않았을 경우
        auctionRepository.findAllByFinalBidder(member).stream()
            .map(auction -> shippingRepository.findByAuction(auction))
            .filter(Optional::isPresent)
            .map(Optional::get)
            .filter(shipping -> !ShippingStatus.DELIVERED.equals(shipping.getShippingStatus()))
            .findFirst()
            .ifPresent(shipping -> {
                throw new CustomException(ErrorStatus._WITHDRAW_CANNOT_CUZ_4);
            });

        // REJECT를 제외한 AuctionScreening이 있는 경우
        if (screeningRepository.existsBySellerAndScreeningStatusNot(member,
            AuctionScreeningStatus.REJECTED)) {
            throw new CustomException(ErrorStatus._WITHDRAW_CANNOT_CUZ_5);
        }

        // 위에서 전부 통과 시 회원 탈퇴 처리 진행
        Member findMember = memberRepository.findById(member.getId()).orElseThrow(
            () -> new CustomException(ErrorStatus._MEMBER_NOT_FOUND)
        );
        findMember.softDelete();
    }
}