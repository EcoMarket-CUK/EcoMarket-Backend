package com.api.jaebichuri.global.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Component
public class ProneAreaApiManager {

    @Value("${api.prone-area.base-url}")
    private String baseUrl;

    @Value("${api.service-key}")
    private String serviceKey;

    @Value("${api.prone-area.search-year}")
    private String searchYear;

    @Value("${api.type}")
    private String type;

    @Value("${api.prone-area.number-of-rows}")
    private int numberOfRows;

    @Value("${api.prone-area.number-of-page}")
    private int numberOfPages;

    private String makeUrl(String siDo, String guGun) {
        return baseUrl +
                "?serviceKey=" + serviceKey +
                "&searchYearCd=" + searchYear +
                "&siDo=" + siDo +
                "&guGun=" + guGun +
                "&type=" + type +
                "&numOfRows=" + numberOfRows +
                "&pageNo=" + numberOfPages;
    }

    public String fetch(String siDo, String guGun) {
        try {
            RestTemplate restTemplate = new RestTemplate();

            String url = makeUrl(siDo, guGun);
            URI uri = new URI(url);

            return restTemplate.getForObject(uri, String.class);
        } catch (Exception e) {
            return "Error occurred while fetching data";
        }
    }

}
