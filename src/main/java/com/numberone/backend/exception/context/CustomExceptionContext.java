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
    BAD_USER_AUTHENTICATION("해당 토큰의 인증 정보가 유효하지 않습니다.", 2005),

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

    // fcm 관련 예외
    FIREBASE_INITIALIZATION_FAILED("Firebase Application 초기 설정에 실패하였습니다.", 6000),
    FIREBASE_MESSAGE_SEND_ERROR("Fcm 푸시 메세지를 보내는 과정에서 오류가 발생했습니다.", 6001),

    //후원 페이지 관련 예외
    NOT_FOUND_SUPPORT("존재하지 않는 후원 관계입니다.", 7000),
    NOT_FOUND_SPONSOR("존재하지 않는 후원입니다.", 7001),
    BAD_REQUEST_HEART("후원을 하기에는 사용자의 마음 갯수가 부족합니다.",7002),

    // article 관련 예외
    NOT_FOUND_ARTICLE("해당 게시글을 찾을 수 없습니다.", 8000),

    // article image 관련 예외
    NOT_FOUND_ARTICLE_IMAGE("해당 이미지를 찾을 수 없습니다.", 9000),
    ;

    private final String message;
    private final int code;
}
