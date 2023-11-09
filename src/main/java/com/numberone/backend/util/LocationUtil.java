package com.numberone.backend.util;

import com.numberone.backend.properties.KakaoProperties;
import com.numberone.backend.util.dto.MapApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
@RequiredArgsConstructor
@Slf4j
//위치(주소나 GPS) 관련 기능들 작성할 util함수
public class LocationUtil {
    private final KakaoProperties kakaoProperties;
    private final RestTemplate restTemplate;

    public String pos2address(double latitude, double longitude) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "KakaoAK " + kakaoProperties.getClient_id());
        URI uri = UriComponentsBuilder
                .fromUriString(kakaoProperties.getMapApiUrl())
                .queryParam("x", longitude)
                .queryParam("y", latitude)
                .build()
                .toUri();
        MapApiResponse mapApiResponse = restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(null, headers), MapApiResponse.class)
                .getBody();
        return mapApiResponse.getDocuments().get(0).getAddress();
    }
}
