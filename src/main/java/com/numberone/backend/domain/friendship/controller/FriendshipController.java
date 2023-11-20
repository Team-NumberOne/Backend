package com.numberone.backend.domain.friendship.controller;

import com.numberone.backend.domain.friendship.dto.response.FriendResponse;
import com.numberone.backend.domain.friendship.dto.response.InviteFriendResponse;
import com.numberone.backend.domain.friendship.dto.response.SendFcmFriendResponse;
import com.numberone.backend.domain.friendship.service.FriendshipService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/friendships")
@RestController
public class FriendshipController {
    private final FriendshipService friendshipService;

    @Operation(summary = "친구(가족) 초대하기", description = """
            - 초대하는 사람의 아이디는 Path variable 으로 inviting-member-id
            - 초대받는 사람의 jwt token 을 header 에 담아서 요청해주세요.
            두 사람은 친구(가족)이 됩니다!
            """)
    @PutMapping("{inviting-member-id}")
    public ResponseEntity<InviteFriendResponse> inviteFriendship(@PathVariable("inviting-member-id") Long memberId){
        InviteFriendResponse response = friendshipService.inviteFriend(memberId);
        return ResponseEntity.created(URI.create(String.format("/api/friendships/%s", memberId)))
                .body(response);
    }

    @Operation(summary = "친구(가족)목록 전부 조회하기", description = """
            사용자의 가족 리스트를 전부 조회합니다.
            jwt 토큰을 반드시 넣어서 요청해주세요.
            """)
    @GetMapping
    public ResponseEntity<List<FriendResponse>> getFriends(){
        return ResponseEntity.ok(friendshipService.getFriends());
    }

    @Operation(summary = "친구(가족) 콕 찔러보기", description = """
            친구(가족)에게 fcm 안부 메세지를 전송합니다. 친구(가족)의 아이디를 path variable 으로 넣어주세요
            jwt 토큰을 반드시 넣어주세요.
            """)
    @GetMapping("{friend-id}")
    public ResponseEntity<SendFcmFriendResponse> sendFcmToFriend(@PathVariable("friend-id") Long friendId){
        return ResponseEntity.ok(friendshipService.sendFcmToFriend(friendId));
    }

}
