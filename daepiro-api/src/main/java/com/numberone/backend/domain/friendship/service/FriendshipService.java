package com.numberone.backend.domain.friendship.service;

import com.numberone.backend.domain.friendship.dto.response.FriendResponse;
import com.numberone.backend.domain.friendship.dto.response.InviteFriendResponse;
import com.numberone.backend.domain.friendship.dto.response.SendFcmFriendResponse;
import com.numberone.backend.domain.friendship.entity.Friendship;
import com.numberone.backend.domain.friendship.repository.FriendshipRepository;
import com.numberone.backend.domain.member.entity.Member;
import com.numberone.backend.domain.member.repository.MemberRepository;
import com.numberone.backend.domain.notification.entity.NotificationEntity;
import com.numberone.backend.domain.notification.entity.NotificationTag;
import com.numberone.backend.domain.notification.repository.NotificationRepository;
import com.numberone.backend.provider.security.SecurityContextProvider;
import com.numberone.backend.exception.badrequest.InvalidInviteTypeException;
import com.numberone.backend.exception.notfound.NotFoundFriendshipException;
import com.numberone.backend.exception.notfound.NotFoundMemberException;
import com.numberone.backend.provider.fcm.service.FcmMessageProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FriendshipService {
    private final MemberRepository memberRepository;
    private final FriendshipRepository friendshipRepository;
    private final FcmMessageProvider fcmMessageProvider;
    private final NotificationRepository notificationRepository;

    @Transactional
    public InviteFriendResponse inviteFriend(Long memberId) {
        long id = SecurityContextProvider.getAuthenticatedUserId();
        Member invitedMember = memberRepository.findById(id)
                .orElseThrow(NotFoundMemberException::new);
        Member member = memberRepository.findById(memberId)
                .orElseThrow(NotFoundMemberException::new);

        List<Member> friendList = member.getFriendships().stream().map(Friendship::getFriend).toList();
        List<Long> friendIdList = friendList.stream().map(Member::getId).toList();

        if (member.getId() == invitedMember.getId() || friendIdList.contains(invitedMember.getId())) {
            throw new InvalidInviteTypeException();
        }


        Friendship savedFriendship = friendshipRepository.save(Friendship.createFriendship(member, invitedMember));
        friendshipRepository.save(Friendship.createFriendship(invitedMember, member));
        return InviteFriendResponse.of(savedFriendship);
    }

    public List<FriendResponse> getFriends() {
        long id = SecurityContextProvider.getAuthenticatedUserId();
        Member member = memberRepository.findById(id)
                .orElseThrow(NotFoundMemberException::new);
        return member.getFriendships()
                .stream().map(friendship -> {
                    Member friend = friendship.getFriend();
                    return FriendResponse.of(friend);
                }).distinct().collect(Collectors.toList());
    }

    @Transactional
    public FriendResponse deleteFriend(Long memberId) {
        long id = SecurityContextProvider.getAuthenticatedUserId();
        Member me = memberRepository.findById(id)
                .orElseThrow(NotFoundMemberException::new);
        Member friend = memberRepository.findById(memberId)
                .orElseThrow(NotFoundMemberException::new);
        Friendship friendship1 = friendshipRepository.findByMemberAndFriend(me, friend)
                .orElseThrow(NotFoundFriendshipException::new);
        Friendship friendship2 = friendshipRepository.findByMemberAndFriend(friend, me)
                .orElseThrow(NotFoundFriendshipException::new);
        friendshipRepository.delete(friendship1);
        friendshipRepository.delete(friendship2);
        return FriendResponse.of(friend);
    }

    @Transactional
    public SendFcmFriendResponse sendFcmToFriend(Long friendId) {
        long id = SecurityContextProvider.getAuthenticatedUserId();
        Member member = memberRepository.findById(id)
                .orElseThrow(NotFoundMemberException::new);
        Member friend = memberRepository.findById(friendId)
                .orElseThrow(NotFoundMemberException::new);

        String memberName = member.getRealName() != null ? member.getRealName() : member.getNickName();
        String friendName = friend.getRealName() != null ? friend.getRealName() : friend.getNickName();

        String title = "긴급!";
        String body = String.format("""
                %s님이 안부를 궁금해하고 있어요.
                걱정하고 있을 %s님을 위해 빨리 연락해주세요!""", memberName, memberName);
        fcmMessageProvider.sendFcm(friend.getFcmToken(), title, body);
        notificationRepository.save(
                new NotificationEntity(friend, NotificationTag.FAMILY, title, body, true)
        );

        return SendFcmFriendResponse.builder()
                .title(title)
                .body(body)
                .build();
    }

}
