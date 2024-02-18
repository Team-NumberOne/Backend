package com.numberone.backend.domain.shelter;

import com.numberone.backend.exception.badrequest.InvalidShelterTypeException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ShelterType {
    CIVIL_DEFENCE,
    FLOOD,
    EARTHQUAKE;

    String code;

    public static ShelterType kor2code(String kor) {
        switch (kor) {
            case "수해" -> {
                return FLOOD;
            }
            case "지진" -> {
                return EARTHQUAKE;
            }
            case "민방위" -> {
                return CIVIL_DEFENCE;
            }
        }
        throw new InvalidShelterTypeException();
    }
}
