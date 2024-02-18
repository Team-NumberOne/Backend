package com.numberone.backend.provider.location;

import com.numberone.backend.properties.KakaoMapProperties;
import com.numberone.backend.provider.location.dto.MapApiResponse;
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
public class LocationProvider {
    private final KakaoMapProperties kakaoProperties;
    private final RestTemplate restTemplate;

    public String pos2address(double latitude, double longitude) {
        try {
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
        } catch (Exception e) {
            log.error("Location Provider occurs error! {}", e.getMessage());
            return "";
        }
    }
}
