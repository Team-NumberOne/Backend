package com.numberone.backend.domain.member.entity;

import com.numberone.backend.domain.support.entity.Support;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    @Comment("마음 갯수")
    private Integer heartCnt;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Support> supports = new ArrayList<>();

    @Builder
    public Member(String email, Integer heartCnt) {
        this.email = email;
        this.heartCnt = heartCnt;
    }

    public static Member of(String email) {
        return Member.builder()
                .email(email)
                .heartCnt(0)
                .build();
    }

    public void increaseHeart(int heart) {
        heartCnt += heart;
    }
}
