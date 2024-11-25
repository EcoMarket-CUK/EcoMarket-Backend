package com.api.jaebichuri.member.entity;

import com.api.jaebichuri.member.enums.Role;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 카카오에서 제공해주는 유저 id + :KAKAO
    private String clientId;

    private String kakaoProfileNickname;

    private String kakaoProfileImage;

    private String name;

    private String nickname;

    private String zipCode;

    private String address;

    private String accountNumber;

    @Enumerated(EnumType.STRING)
    private Role role;

    public void updateKakaoProfileNickname(String name) {
        this.kakaoProfileNickname = name;
    }

    public void updateRole(Role newRole) { this.role = newRole; }

    public void updateInfo(String name, String nickname, String zipcode, String address) {
        this.name = name;
        this.nickname = nickname;
        this.zipCode= zipcode;
        this.address = address;
    }

    public void updateKakaoProfileImage(String kakaoProfileImage) {
        this.kakaoProfileImage = kakaoProfileImage;
    }
}