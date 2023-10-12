package com.numberone.backend.exception.context;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CustomExceptionContext implements ExceptionContext {
    // MEMBER 관련 예외
    NOT_FOUND_MEMBER("존재하지 않는 회원을 조회할 수 없습니다.", 1000),


    // SHELTER 관련 예외
    NOT_FOUND_SHELTER("주변에 가까운 대피소가 존재하지 않습니다.", 2000)
    ;



    /** 2023. 10. 02. versatile0010

        이 곳에서 각 예외 별 메세지와 예외 코드를 관리합니다.

        예를 들어,
        // MEMBER
        NOT_FOUND_MEMBER("존재하지 않는 회원을 조회할 수 없습니다.", 2000),
        DUPLICATED_MEMBER_NAME("회원의 이름은 중복될 수 없습니다, 2001),
        // STATION
        NOT_FOUND_STATION("존재하지 않는 지하철역을 조회할 수 없습니다.", 3000);
        과 같이 작성할 수 있습니다.

     */
    private final String message;
    private final int code;
}
