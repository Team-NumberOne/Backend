package com.numberone.backend.domain.friendship.service;

import com.numberone.backend.domain.friendship.dto.response.FriendResponse;
import com.numberone.backend.domain.friendship.dto.response.InviteFriendResponse;
import com.numberone.backend.domain.friendship.dto.response.SendFcmFriendResponse;
import com.numberone.backend.domain.friendship.entity.Friendship;
import com.numberone.backend.domain.friendship.repository.FriendshipRepository;
import com.numberone.backend.domain.member.entity.Member;
import com.numberone.backend.domain.member.repository.MemberRepository;
import com.numberone.backend.domain.notification.entity.NotificationTag;
import com.numberone.backend.domain.token.util.SecurityContextProvider;
import com.numberone.backend.exception.badrequest.InvalidInviteTypeException;
import com.numberone.backend.exception.notfound.NotFoundMemberException;
import com.numberone.backend.support.fcm.service.FcmMessageProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FriendshipService {
    private final MemberRepository memberRepository;
    private final FriendshipRepository friendshipRepository;
    private final FcmMessageProvider fcmMessageProvider;

    @Transactional
    public InviteFriendResponse inviteFriend(Long memberId) {
        String principal = SecurityContextProvider.getAuthenticatedUserEmail();
        Member invitedMember = memberRepository.findByEmail(principal)
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
        String principal = SecurityContextProvider.getAuthenticatedUserEmail();
        Member member = memberRepository.findByEmail(principal)
                .orElseThrow(NotFoundMemberException::new);
        return member.getFriendships()
                .stream().map(friendship -> {
                    Member friend = friendship.getFriend();
                    return FriendResponse.of(friend);
                }).distinct().collect(Collectors.toList());
    }

    @Transactional
    public SendFcmFriendResponse sendFcmToFriend(Long friendId) {
        String principal = SecurityContextProvider.getAuthenticatedUserEmail();
        Member member = memberRepository.findByEmail(principal)
                .orElseThrow(NotFoundMemberException::new);
        Member friend = memberRepository.findById(friendId)
                .orElseThrow(NotFoundMemberException::new);

        String memberName = member.getRealName() != null ? member.getRealName() : member.getNickName();
        String friendName = friend.getRealName() != null ? friend.getRealName() : friend.getNickName();

        String title = String.format("[ %s님! %s님께서 회원님이 안전한지 걱정하고 있어요. 🥲]", friendName, memberName);
        String body = String.format(" %s님께 현재 상태를 보내볼까요? ", memberName);

        fcmMessageProvider.sendFcm(friend, title, body, NotificationTag.FAMILY);

        return SendFcmFriendResponse.builder()
                .title(title)
                .body(body)
                .build();
    }

}
