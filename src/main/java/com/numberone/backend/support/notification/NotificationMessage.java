package com.numberone.backend.support.notification;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NotificationMessage implements NotificationMessageSpec {

    ARTICLE_COMMENT_FCM_ALARM("[대피로 알림]", "게시글에 댓글이 달렸어요!", null),
    BEST_ARTICLE_FCM_ALARM("[대피로 알림]", "축하드립니다! 베스트 게시글로 선정되었습니다. 🎉", null);
    ;

    private final String title;
    private final String body;
    private final String imageUrl;

}
