package com.numberone.backend.domain.disaster.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DisasterDataResponse {

    @JsonProperty("DisasterMsg")
    private List<Message> disasterMsg;

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Message {

        @JsonProperty("head")
        private List<Head> head;

        @JsonProperty("row")
        private List<RowItem> rowItems;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Head {

        @JsonProperty("totalCount")
        private Integer totalCount;

        @JsonProperty("numOfRows")
        private String numOfRows;

        @JsonProperty("pageNo")
        private String pageNo;

        @JsonProperty("type")
        private String type;

        @JsonProperty("RESULT")
        private Result result;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Result {

        @JsonProperty("resultCode")
        private String resultCode;

        @JsonProperty("resultMsg")
        private String resultMsg;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class RowItem {

        @JsonProperty("create_date")
        private String createDate;

        @JsonProperty("location_id")
        private String locationId;

        @JsonProperty("location_name")
        private String locationName;

        @JsonProperty("md101_sn")
        private String msgId;

        private String msg;

        @JsonProperty("send_platform")
        private String sendPlatform;
    }
}
