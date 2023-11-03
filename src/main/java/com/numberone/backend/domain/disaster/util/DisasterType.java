package com.numberone.backend.domain.disaster.util;

import com.numberone.backend.exception.badrequest.InvalidDisasterTypeException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DisasterType {
    TYPHOON("태풍"),
    DRYNESS("건조"),
    WILDFIRE("산불"),
    LANDSLIDE("산사태"),
    FLOOD("홍수"),
    HEAVY_RAIN("호우"),
    HEATWAVE("폭염"),
    FOG("안개"),
    ROUGH_SEA("풍랑"),
    FINE_DUST("미세먼지"),
    TIDAL_WAVE("대조기"),
    DROUGHT("가뭄"),
    HEAVY_SNOWFALL("대설"),
    TSUNAMI("지진해일"),
    EARTHQUAKE("지진"),
    COLD_WAVE("한파"),
    YELLOW_DUST("황사"),
    STRONG_WIND("강풍"),
    TRAFFIC_CONTROL("교통통제"),
    FIRE("화재"),
    COLLAPSE("붕괴"),
    EXPLOSION("폭발"),
    TRAFFIC_ACCIDENT("교통사고"),
    ENVIRONMENTAL_POLLUTION("환경오염사고"),
    ENERGY("에너지"),
    COMMUNICATION("통신"),
    TRAFFIC("교통"),
    FINANCE("금융"),
    MEDICAL("의료"),
    WATER_SUPPLY("수도"),
    INFECTIOUS_DISEASE("전염병"),
    POWER_OUTAGE("정전"),
    GAS("가스"),
    AI("AI"),
    CHEMICAL("화생방사고"),
    RIOT("폭동"),
    TERROR("테러"),
    EMERGENCY("비상사태"),
    CIVIL_DEFENSE("민방공"),
    OTHERS("기타");

    private final String description;

    public String getDescription() {
        return description;
    }

    public static DisasterType kor2code(String kor) {
        for (DisasterType type : DisasterType.values()) {
            if (type.getDescription().equals(kor)) {
                return type;
            }
        }
        throw new InvalidDisasterTypeException();
    }
}