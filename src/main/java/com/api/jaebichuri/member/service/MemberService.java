package com.api.jaebichuri.member.service;

import com.api.jaebichuri.auth.dto.TokenResponseDto;
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

    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public TokenResponseDto successSocialLogin(Map<String, String> memberInfo) {
        String clientId = memberInfo.get(JSON_ATTRIBUTE_NAME_ID);
        String nickname = memberInfo.get(JSON_ATTRIBUTE_NAME_NICKNAME);
        String accessToken = jwtUtil.generateAccessToken(clientId);
        String refreshToken = jwtUtil.generateRefreshToken(clientId);

        Optional<Member> findMember = memberRepository.findByClientId(clientId);

        if (findMember.isPresent()) {
            // 최초 로그인이 아닌 사용자의 경우 카카오에서 사용하는 닉네임을 변경했을 가능성이 있기 때문에 로그인 시 업데이트 해준다.
            Member member = findMember.get();
            member.updateNickname(nickname);
            member.saveRefreshToken(refreshToken);

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
            .refreshToken(refreshToken)
            .build();

        memberRepository.save(member);

        // jwt 엑세스 토큰 응답
        return TokenResponseDto.builder()
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .build();
    }

    public TokenResponseDto reissue(String refreshToken) {
        String clientId = jwtUtil.extractClientId(refreshToken);
        String accessToken = jwtUtil.generateAccessToken(clientId);

        // 검증
        checkMemberRefreshToken(clientId, refreshToken);

        // 검증 성공 시 accessToken 재발급 + refreshToken은 원래 토큰 전달
        return TokenResponseDto.builder()
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .build();
    }

    @Transactional
    public void deleteRefreshToken(Member member) {
        if (member.getRefreshToken() == null) {
            throw new CustomException(ErrorStatus._MEMBER_ALREADY_LOGOUT);
        }

        // refreshToken 값 null로 업데이트, 논리적 삭제
        member.deleteRefreshToken();

        //member는 준영속 상태이기 때문에 영속화 시킨다.(save = persist) -> 변경 감지로 update
        memberRepository.save(member);
    }

    private void checkMemberRefreshToken(String clientId, String refreshToken) {
        Member member = memberRepository.findByClientId(clientId).orElseThrow(
            () -> new CustomException(ErrorStatus._MEMBER_NOT_FOUND));

        // 멤버에 refreshtoken이 없다면? -> 로그아웃하면 디비에서 refreshToken value 지움
        if (member.getRefreshToken().isEmpty()) {
            throw new CustomException(ErrorStatus._MEMBER_TOKEN_NOT_FOUND);
        }

        // 멤버의 리프레시 토큰 값과 전달 받은 리프레시 토큰 값이 다른 경우 (이런 경우가 발생할 일은 없음)
        if (!refreshToken.equals(member.getRefreshToken())) {
            throw new CustomException(ErrorStatus._MEMBER_TOKEN_MISMATCH);
        }
    }

}