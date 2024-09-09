package com.api.jaebichuri.accident.prone.controller;

import com.api.jaebichuri.accident.prone.dto.ProneAreaReq;
import com.api.jaebichuri.accident.prone.dto.ProneAreaRes;
import com.api.jaebichuri.accident.prone.service.ProneAreaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/accident-prone-area")
@RequiredArgsConstructor
public class ProneAreaController {

    private final ProneAreaService proneAreaService;

    @PostMapping("/details")
    public ResponseEntity<List<ProneAreaRes>> getAccidentProneArea(@Valid @RequestBody ProneAreaReq request) {
        return ResponseEntity.ok(proneAreaService.getAccidentProneArea(request));
    }

}
