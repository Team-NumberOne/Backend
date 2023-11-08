package com.numberone.backend.domain.disaster.util;

import com.numberone.backend.exception.badrequest.InvalidDisasterTypeException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DisasterType {
    CIVIL_DEFENSE("민방공"),
    TERROR("테러"),
    LANDSLIDE("산사태"),
    EARTHQUAKE("지진"),
    TSUNAMI("해일"),
    FLOOD("홍수"),
    TYPHOON("태풍"),
    WILDFIRE("산불"),
    NATURAL_DISASTER("자연재난"),
    INFECTIOUS_DISEASE("감염병"),
    FIRE("화재"),
    OTHERS("기타");

    private final String description;

    public String getDescription() {
        return description;
    }

    public static DisasterType kor2code(String kor) {
        switch (kor) {
            case "민방공" -> {
                return CIVIL_DEFENSE;
            }
            case "테러" -> {
                return TERROR;
            }
            case "산사태" -> {
                return LANDSLIDE;
            }
            case "지진" -> {
                return EARTHQUAKE;
            }
            case "지진해일" -> {
                return TSUNAMI;
            }
            case "홍수" -> {
                return FLOOD;
            }
            case "태풍" -> {
                return TYPHOON;
            }
            case "산불" -> {
                return WILDFIRE;
            }
            case "건조", "폭염", "미세먼지", "대조기", "가뭄", "대설", "한파", "황사", "강풍", "호우", "풍랑", "안개" -> {
                return NATURAL_DISASTER;
            }
            case "전염병" -> {
                return INFECTIOUS_DISEASE;
            }
            case "화재" -> {
                return FIRE;
            }
            case "화생방사고", "폭동", "비상사태", "기타", "교통통제", "붕괴", "폭발", "교통사고", "환경오염사고", "에너지", "통신", "교통", "금융", "의료", "수도", "정전", "가스", "AI" -> {
                return OTHERS;
            }
        }
        throw new InvalidDisasterTypeException();
    }
}