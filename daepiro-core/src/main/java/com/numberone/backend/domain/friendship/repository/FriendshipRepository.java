package com.numberone.backend.domain.friendship.repository;

import com.numberone.backend.domain.friendship.entity.Friendship;
import com.numberone.backend.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FriendshipRepository extends JpaRepository<Friendship, Long> {
    Optional<Friendship> findByMemberAndFriend(Member member, Member friend);
}
