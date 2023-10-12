package com.numberone.backend.domain.shelter.util;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAddress is a Querydsl query type for Address
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QAddress extends BeanPath<Address> {

    private static final long serialVersionUID = 1799323203L;

    public static final QAddress address = new QAddress("address");

    public final NumberPath<Double> coordinateX = createNumber("coordinateX", Double.class);

    public final NumberPath<Double> coordinateY = createNumber("coordinateY", Double.class);

    public final StringPath fullAddress = createString("fullAddress");

    public final NumberPath<Double> latitude = createNumber("latitude", Double.class);

    public final NumberPath<Double> longitude = createNumber("longitude", Double.class);

    public final StringPath roadNameAddress = createString("roadNameAddress");

    public final StringPath roadNamePostalCode = createString("roadNamePostalCode");

    public QAddress(String variable) {
        super(Address.class, forVariable(variable));
    }

    public QAddress(Path<? extends Address> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAddress(PathMetadata metadata) {
        super(Address.class, metadata);
    }

}

