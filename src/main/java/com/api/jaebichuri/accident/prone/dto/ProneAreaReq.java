package com.api.jaebichuri.accident.prone.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ProneAreaReq {

    @NotBlank
    private String siDo;

    @NotBlank
    private String guGun;

    /*@NotNull
    private Double latitude;

    @NotNull
    private Double longitude;*/

}
