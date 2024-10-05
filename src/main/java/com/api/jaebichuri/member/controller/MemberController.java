package com.api.jaebichuri.member.controller;

import com.api.jaebichuri.member.service.MemberService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
@Tag(name = "멤버 API")
public class MemberController {

    private final MemberService memberService;
}