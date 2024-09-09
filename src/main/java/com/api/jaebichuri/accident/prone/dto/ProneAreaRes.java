package com.api.jaebichuri.accident.prone.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProneAreaRes {

    private String districtName;

    private String address;

    private Double latitude;

    private Double longitude;

    // 사고 다발 지역 내 사고 건수
    private int totalAccidents;

    // 사고 다발 지역 내 사상자 수
    private int totalCasualties;

    // 사고 다발 지역 내 사망자 수
    private int totalFatalities;

    // 사고 다발 지역 내 중상자 수
    private int totalSeriousInjuries;

    // 사고 다발 지역 내 경상자 수
    private int totalMinorInjuries;

}
