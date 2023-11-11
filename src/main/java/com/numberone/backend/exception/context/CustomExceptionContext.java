package com.numberone.backend.exception.context;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CustomExceptionContext implements ExceptionContext {
    // MEMBER 관련 예외
    NOT_FOUND_MEMBER("존재하지 않는 회원을 조회할 수 없습니다.", 1000),

    // TOKEN 관련 예외
    WRONG_ACCESS_TOKEN("존재하지 않는 액세스 토큰입니다.", 2001),
    EXPIRED_ACCESS_TOKEN("만료된 액세스 토큰입니다. 리프레쉬 토큰을 이용하여 갱신해주세요.", 2002),
    WRONG_REFRESH_TOKEN("존재하지 않거나 만료된 리프레쉬 토큰입니다. 다시 리프레쉬 토큰을 발급받아주세요.", 2003),
    BAD_REQUEST_SOCIAL_TOKEN("요청하신 네이버 또는 카카오 소셜 토큰이 유효하지 않습니다.", 2004),

    // SHELTER 관련 예외
    NOT_FOUND_SHELTER("주변에 가까운 대피소가 존재하지 않습니다.", 3000),
    INVALID_SHELTER_TYPE("올바른 대피소 유형을 입력해주세요. (민방위, 수해, 지진) ", 3001),

    // S3 관련 예외
    S3_FILE_UPLOAD_FAILED("S3 파일 업로드에 실패했습니다.", 4000),
    S3_MULTIPART_MISSING("Multipart 파일이 null 이거나 없습니다.", 4001),

    // DISASTER 관련 예외
    INVALID_DISASTER_TYPE("존재하지 않는 재난 유형입니다.",5000),
    NOT_FOUND_API("데이터 수집을 위한 API 요청이 실패했습니다.",5001),
    NOT_FOUND_CRAWLING("데이터 수집을 위한 크롤링이 실패했습니다.",5002),

    //후원 페이지 관련 예외
    NOT_FOUND_SUPPORT("존재하지 않는 후원 관계입니다.", 7000),
    NOT_FOUND_SPONSOR("존재하지 않는 후원입니다.", 7001)
    ;


    /**
     * 2023. 10. 02. versatile0010
     * <p>
     * 이 곳에서 각 예외 별 메세지와 예외 코드를 관리합니다.
     * <p>
     * 예를 들어,
     * // MEMBER
     * NOT_FOUND_MEMBER("존재하지 않는 회원을 조회할 수 없습니다.", 2000),
     * DUPLICATED_MEMBER_NAME("회원의 이름은 중복될 수 없습니다, 2001),
     * // STATION
     * NOT_FOUND_STATION("존재하지 않는 지하철역을 조회할 수 없습니다.", 3000);
     * 과 같이 작성할 수 있습니다.
     */
    private final String message;
    private final int code;
}
