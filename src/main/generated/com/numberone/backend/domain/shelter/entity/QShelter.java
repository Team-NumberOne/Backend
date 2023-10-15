package com.numberone.backend.domain.shelter.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QShelter is a Querydsl query type for Shelter
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QShelter extends EntityPathBase<Shelter> {

    private static final long serialVersionUID = 932083541L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QShelter shelter = new QShelter("shelter");

    public final com.numberone.backend.config.basetime.QBaseTimeEntity _super = new com.numberone.backend.config.basetime.QBaseTimeEntity(this);

    public final com.numberone.backend.domain.shelter.util.QAddress address;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath districtCode = createString("districtCode");

    public final StringPath facilityFullName = createString("facilityFullName");

    public final StringPath facilityTypeName = createString("facilityTypeName");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath managementNumber = createString("managementNumber");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final EnumPath<com.numberone.backend.domain.shelter.util.ShelterStatus> status = createEnum("status", com.numberone.backend.domain.shelter.util.ShelterStatus.class);

    public QShelter(String variable) {
        this(Shelter.class, forVariable(variable), INITS);
    }

    public QShelter(Path<? extends Shelter> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QShelter(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QShelter(PathMetadata metadata, PathInits inits) {
        this(Shelter.class, metadata, inits);
    }

    public QShelter(Class<? extends Shelter> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.address = inits.isInitialized("address") ? new com.numberone.backend.domain.shelter.util.QAddress(forProperty("address")) : null;
    }

}

