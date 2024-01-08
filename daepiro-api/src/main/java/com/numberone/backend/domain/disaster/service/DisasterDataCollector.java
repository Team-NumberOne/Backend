package com.numberone.backend.domain.disaster.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.numberone.backend.domain.disaster.dto.request.SaveDisasterRequest;
import com.numberone.backend.domain.disaster.dto.response.DisasterDataResponse;
import com.numberone.backend.domain.disaster.repository.DisasterRepository;
import com.numberone.backend.domain.disaster.util.DisasterType;
import com.numberone.backend.exception.notfound.NotFoundApiException;
import com.numberone.backend.exception.notfound.NotFoundCrawlingException;
import com.numberone.backend.properties.DisasterProperties;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class DisasterDataCollector {
    private final RestTemplate restTemplate;
    private final DisasterProperties disasterProperties;
    private final ObjectMapper objectMapper;
    private final DisasterRepository disasterRepository;
    private final DisasterService disasterService;
    private Long latestDisasterNum = 0L;
    private Map<Long, DisasterType> disasterTypeMap = new HashMap<>();

    @PostConstruct
    public void init() {
        disasterRepository.findTopByOrderByDisasterNumDesc().ifPresent(
                disaster -> {
                    latestDisasterNum = disaster.getDisasterNum();
                }
        );
        log.info("init latestDisasterNum = " + latestDisasterNum);
    }

    @Scheduled(fixedDelay = 60 * 1000)
    public void collectData() {
        log.info("[ Disaster data Collector is running! ] ");
        URI uri = UriComponentsBuilder
                .fromUriString(disasterProperties.getApiUrl())
                .queryParam("ServiceKey", disasterProperties.getSecretKey())
                .queryParam("pageNo", 1)
                .queryParam("numOfRows", 10)
                .queryParam("type", "json")
                .build(true)
                .toUri();
        String responseString = restTemplate.getForObject(uri, String.class);
        DisasterDataResponse disasterDataResponse = null;
        try {
            disasterDataResponse = objectMapper.readValue(responseString, DisasterDataResponse.class);
        } catch (JsonProcessingException e) {
            throw new NotFoundApiException();
        }
        List<DisasterDataResponse.RowItem> disasters = disasterDataResponse.getDisasterMsg().get(1).getRowItems();
        Long topDisasterNum = Long.parseLong(disasters.get(0).getMsgId());
        if (topDisasterNum > latestDisasterNum) {
            log.info("new disaster");
            crawlingDisasterTypeV2();
            if (disasterTypeMap.size() != disasters.size())
                throw new NotFoundCrawlingException();
            for (DisasterDataResponse.RowItem disaster : disasters) {
                Long disasterNum = Long.parseLong(disaster.getMsgId());
                if (disasterNum <= latestDisasterNum)
                    break;
                String[] locations = Arrays.stream(disaster.getLocationName().split(","))
                        .distinct()
                        .toArray(String[]::new);
                for (String loc : locations) {
                    if (disasterTypeMap.get(disasterNum).equals(DisasterType.OTHERS) &&
                            (disaster.getMsg().contains("실종") || disaster.getMsg().contains("목격") || disaster.getMsg().contains("배회")))
                        disasterTypeMap.put(disasterNum, DisasterType.MISSING);
                    disasterService.save(SaveDisasterRequest.of(
                            disasterTypeMap.get(disasterNum),
                            loc.replace(" 전체", ""),//이 부분은 메시지 내부 파싱하여 더 정확한 주소를 저장하도록 수정해야함
                            disaster.getMsg(),
                            disasterNum,
                            disaster.getCreateDate()
                    ));
                }
            }
            latestDisasterNum = topDisasterNum;
        }
    }

//    private void crawlingDisasterType() {
//        disasterTypeMap.clear();
//        WebClient webClient = new WebClient();
//        webClient.getOptions().setJavaScriptEnabled(true);
//        webClient.getOptions().setCssEnabled(false);
//        HtmlPage htmlPage = null;
//        try {
//            htmlPage = webClient.getPage(disasterProperties.getCrawlingUrl());
//        } catch (IOException e) {
//            throw new NotFoundCrawlingException();
//        }
//        webClient.waitForBackgroundJavaScript(5000);
//        Document doc = Jsoup.parse(htmlPage.asXml());
//        Elements nums = doc.select("[id^=disasterSms_tr_][id$=_MD101_SN]");
//        Elements types = doc.select("[id^=disasterSms_tr_][id$=_DSSTR_SE_NM]");
//        for (int i = 0; i < nums.size(); i++) {
//            disasterTypeMap.put(
//                    Long.parseLong(nums.get(i).text()),
//                    DisasterType.kor2code(types.get(i).text())
//            );
//        }
//    }


    private void crawlingDisasterTypeV2() {
        disasterTypeMap.clear();
        try (WebClient webClient = new WebClient()) {
            webClient.getOptions().setJavaScriptEnabled(true);
            webClient.getOptions().setCssEnabled(false);
            HtmlPage htmlPage = webClient.getPage(disasterProperties.getCrawlingUrl());
            webClient.waitForBackgroundJavaScript(5000);
            Document doc = Jsoup.parse(htmlPage.asXml());
            Elements nums = doc.select("[id^=disasterSms_tr_][id$=_MD101_SN]");
            Elements types = doc.select("[id^=disasterSms_tr_][id$=_DSSTR_SE_NM]");
            for (int i = 0; i < nums.size(); i++) {
                disasterTypeMap.put(
                        Long.parseLong(nums.get(i).text()),
                        DisasterType.kor2code(types.get(i).text())
                );
            }
        } catch (IOException e) {
            throw new NotFoundCrawlingException();
        }
    }

}
