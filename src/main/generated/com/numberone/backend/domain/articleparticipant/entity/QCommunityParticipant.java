package com.numberone.backend.domain.articleparticipant.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCommunityParticipant is a Querydsl query type for CommunityParticipant
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCommunityParticipant extends EntityPathBase<ArticleParticipant> {

    private static final long serialVersionUID = -1555403901L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCommunityParticipant communityParticipant = new QCommunityParticipant("communityParticipant");

    public final com.numberone.backend.config.basetime.QBaseTimeEntity _super = new com.numberone.backend.config.basetime.QBaseTimeEntity(this);

    public final com.numberone.backend.domain.article.entity.QArticle article;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isOwner = createBoolean("isOwner");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public QCommunityParticipant(String variable) {
        this(ArticleParticipant.class, forVariable(variable), INITS);
    }

    public QCommunityParticipant(Path<? extends ArticleParticipant> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCommunityParticipant(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCommunityParticipant(PathMetadata metadata, PathInits inits) {
        this(ArticleParticipant.class, metadata, inits);
    }

    public QCommunityParticipant(Class<? extends ArticleParticipant> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.article = inits.isInitialized("article") ? new com.numberone.backend.domain.article.entity.QArticle(forProperty("article")) : null;
    }

}

