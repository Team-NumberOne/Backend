package com.numberone.backend.util.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MapApiResponse {
    private Meta meta;
    private List<Doc> documents;

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Meta{
        @JsonProperty("total_count")
        private Integer totalCount;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Doc{
        @JsonProperty("result_type")
        private String resultType;

        private String code;

        @JsonProperty("address_name")
        private String address;

        @JsonProperty("region_1depth_name")
        private String region1;
        @JsonProperty("region_2depth_name")
        private String region2;

        @JsonProperty("region_3depth_name")
        private String region3;
        @JsonProperty("region_4depth_name")
        private String region4;

        private Double x;

        private Double y;
    }
}
