package com.api.jaebichuri.member.service;

import com.api.jaebichuri.auth.dto.TokenResponseDto;
import com.api.jaebichuri.auth.repository.RefreshTokenRepository;
import com.api.jaebichuri.global.response.code.status.ErrorStatus;
import com.api.jaebichuri.global.response.exception.CustomException;
import com.api.jaebichuri.member.entity.Member;
import com.api.jaebichuri.member.enums.Role;
import com.api.jaebichuri.member.repository.MemberRepository;
import com.api.jaebichuri.auth.jwt.JwtUtil;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private static final String JSON_ATTRIBUTE_NAME_NICKNAME = "nickname";
    private static final String JSON_ATTRIBUTE_NAME_ID = "id";

    private final RefreshTokenRepository refreshTokenRepository;
    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public TokenResponseDto login(Map<String, String> memberInfo) {
        String clientId = memberInfo.get(JSON_ATTRIBUTE_NAME_ID);
        String nickname = memberInfo.get(JSON_ATTRIBUTE_NAME_NICKNAME);
        String accessToken = jwtUtil.generateAccessToken(clientId);
        String refreshToken = jwtUtil.generateRefreshToken(clientId);

        //redis에 refresh token 저장
        //key: clientId, value: refresh token
        refreshTokenRepository.save(clientId, refreshToken);

        Optional<Member> findMember = memberRepository.findByClientId(clientId);

        if (findMember.isPresent()) {
            // 최초 로그인이 아닌 사용자의 경우 카카오에서 사용하는 닉네임을 변경했을 가능성이 있기 때문에 로그인 시 업데이트 해준다.
            Member member = findMember.get();
            member.updateNickname(nickname);

            // jwt 엑세스 토큰 응답
            return TokenResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
        }

        // 최초 로그인 하는 사용자의 경우 db에 사용자 정보를 저장한다.
        Member member = Member.builder()
            .clientId(clientId)
            .nickname(nickname)
            .role(Role.USER)
            .build();

        memberRepository.save(member);

        // jwt 엑세스 토큰 응답
        return TokenResponseDto.builder()
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .build();
    }
}