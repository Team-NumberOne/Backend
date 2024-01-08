package com.numberone.backend.domain.articleparticipant.entity;

import com.numberone.backend.config.basetime.BaseTimeEntity;
import com.numberone.backend.domain.article.entity.Article;
import com.numberone.backend.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Comment("동네생활 게시글 참여자")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "ARTICLE_PARTICIPANT")
public class ArticleParticipant extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_participant_id")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "article_id")
    private Article article;

    @Comment("회원 id")
    private Long memberId;

    public ArticleParticipant(Article article, Member member){
        this.article = article;
        this.memberId= member.getId();
    }
}
