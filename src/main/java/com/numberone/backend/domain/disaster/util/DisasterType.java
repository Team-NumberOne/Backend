package com.numberone.backend.domain.disaster.util;

import com.numberone.backend.exception.badrequest.InvalidDisasterTypeException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DisasterType {
    //자연재난
    DROUGHT("가뭄"),
    STRONG_WIND("강풍"),
    DRYNESS("건조"),
    HEAVY_SNOWFALL("대설"),
    TIDAL_WAVE("대조기"),
    FINE_DUST("미세먼지"),
    WILDFIRE("산불"),
    LANDSLIDE("산사태"),
    FOG("안개"),
    EARTHQUAKE("지진"),
    TYPHOON("태풍"),
    HEATWAVE("폭염"),
    ROUGH_SEA("풍랑"),
    COLD_WAVE("한파"),
    HEAVY_RAIN("호우"),
    FLOOD("홍수"),

    //사회재난
    GAS("가스"),
    TRAFFIC("교통"),
    FINANCE("금융"),
    COLLAPSE("붕괴"),
    WATER_SUPPLY("수도"),
    ENERGY("에너지"),
    MEDICAL("의료"),
    INFECTIOUS_DISEASE("전염병"),
    POWER_OUTAGE("정전"),
    COMMUNICATION("통신"),
    EXPLOSION("폭발"),
    FIRE("화재"),
    ENVIRONMENTAL_POLLUTION("환경오염사고"),
    AI("AI"),

    //비상대비
    EMERGENCY("비상사태"),
    TERROR("테러"),
    CHEMICAL("화생방사고"),

    //기타
    MISSING("실종"),
    OTHERS("기타"),

    //추후 삭제예정
    NATURAL_DISASTER("자연재난");

    private final String description;

    public String getDescription() {
        return description;
    }

    public static DisasterType kor2code(String kor) {
        for (DisasterType type : values()) {
            if (kor.equals(type.getDescription()))
                return type;
        }
        //1대1 매핑이 되지 않는 카테고리들 처리
        switch (kor) {
            case "지진해일" -> {
                return EARTHQUAKE;
            }
            case "황사" -> {
                return OTHERS;
            }
            case "교통통제", "교통사고" -> {
                return TRAFFIC;
            }
            case "폭동", "민방공" -> {
                return EMERGENCY;
            }
        }
        throw new InvalidDisasterTypeException();
    }
}