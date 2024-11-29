package com.api.jaebichuri.member.mapper;

import com.api.jaebichuri.member.dto.MemberInfoResponseDTO;
import com.api.jaebichuri.member.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MemberMapper {

    @Mapping(source = "member.name", target = "name")
    @Mapping(source = "member.nickname", target = "nickname")
    @Mapping(source = "member.zipCode", target = "zipCode")
    @Mapping(source = "member.address", target = "address")
    MemberInfoResponseDTO toMemberInfoResponseDTO(Member member);
}