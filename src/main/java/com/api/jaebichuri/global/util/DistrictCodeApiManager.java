package com.api.jaebichuri.global.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class DistrictCodeApiManager {

    @Value("${api.district-code.base-url}")
    private String baseUrl;

    @Value("${api.service-key}")
    private String serviceKey;

    @Value("${api.district-code.number-of-page}")
    private int numberOfPages;

    @Value("${api.district-code.number-of-rows}")
    private int numberOfRows;

    @Value("${api.type}")
    private String type;

    private String makeUrl(String siDo, String guGun) {
        String districtName = siDo + " " + guGun;
        String encodedDistrictName;

        try {
            encodedDistrictName = URLEncoder.encode(districtName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("UTF-8 encoding is not supported", e);
        }

        return baseUrl +
                "?serviceKey=" + serviceKey +
                "&pageNo=" + numberOfPages +
                "&numOfRows=" + numberOfRows +
                "&type=" + type +
                "&locatadd_nm=" + encodedDistrictName;
    }

    public Map<String, String> getDistrictCodes(String siDo, String guGun) {
        RestTemplate restTemplate = new RestTemplate();

        try {
            String url = makeUrl(siDo, guGun);
            URI uri = new URI(url);

            String jsonString = restTemplate.getForObject(uri, String.class);

            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> responseBody = objectMapper.readValue(jsonString, new TypeReference<Map<String, Object>>() {});

            List<Map<String, Object>> stanReginCdList = (List<Map<String, Object>>) responseBody.get("StanReginCd");

            if (stanReginCdList.size() > 1) {
                Map<String, Object> stanReginCd = stanReginCdList.get(1);
                List<Map<String, Object>> rows = (List<Map<String, Object>>) stanReginCd.get("row");

                if (!rows.isEmpty()) {
                    Map<String, Object> row = rows.get(0);
                    Map<String, String> result = new HashMap<>();
                    result.put("siDo", (String) row.get("sido_cd"));
                    result.put("guGun", (String) row.get("sgg_cd"));

                    return result;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while fetching district codes", e);
        }

        throw new RuntimeException("Invalid response format or no data found");
    }

}
