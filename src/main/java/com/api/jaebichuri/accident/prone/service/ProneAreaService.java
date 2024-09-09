package com.api.jaebichuri.accident.prone.service;

import com.api.jaebichuri.accident.prone.dto.ProneAreaReq;
import com.api.jaebichuri.accident.prone.dto.ProneAreaRes;
import com.api.jaebichuri.global.util.ProneAreaApiManager;
import com.api.jaebichuri.global.util.DistrictCodeApiManager;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProneAreaService {

    private final ProneAreaApiManager proneAreaApiManager;
    private final DistrictCodeApiManager districtCodeApiManager;

    public List<ProneAreaRes> getAccidentProneArea(ProneAreaReq request) {
        Map<String, String> districtCodes = districtCodeApiManager.getDistrictCodes(request.getSiDo(), request.getGuGun());

        String siDo = districtCodes.get("siDo");
        String guGun = districtCodes.get("guGun");
        String json = proneAreaApiManager.fetch(siDo, guGun);

        try {
            return convertJsonToDto(json);
        } catch (IOException e) {
            throw new RuntimeException("Error processing the JSON response", e);
        }
    }

    private List<ProneAreaRes> convertJsonToDto(String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode rootNode = objectMapper.readTree(json);
        JsonNode itemsNode = rootNode.path("items").path("item");

        List<ProneAreaRes> proneAreaList = new ArrayList<>();

        for(JsonNode node : itemsNode) {
            ProneAreaRes response = new ProneAreaRes();
            response.setDistrictName(node.path("sido_sgg_nm").asText());
            response.setAddress(node.path("spot_nm").asText());
            response.setLatitude(node.path("la_crd").asDouble());
            response.setLongitude(node.path("lo_crd").asDouble());
            response.setTotalAccidents(node.path("occrrnc_cnt").asInt());
            response.setTotalCasualties(node.path("caslt_cnt").asInt());
            response.setTotalFatalities(node.path("dth_dnv_cnt").asInt());
            response.setTotalSeriousInjuries(node.path("se_dnv_cnt").asInt());
            response.setTotalMinorInjuries(node.path("sl_dnv_cnt").asInt());

            proneAreaList.add(response);
        }

        return proneAreaList;
    }

}
