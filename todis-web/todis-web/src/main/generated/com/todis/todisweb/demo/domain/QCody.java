package com.todis.todisweb.demo.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCody is a Querydsl query type for Cody
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCody extends EntityPathBase<Cody> {

    private static final long serialVersionUID = 743877413L;

    public static final QCody cody = new QCody("cody");

    public final NumberPath<Integer> clothId = createNumber("clothId", Integer.class);

    public final StringPath comment = createString("comment");

    public final DateTimePath<java.sql.Timestamp> date = createDateTime("date", java.sql.Timestamp.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath image = createString("image");

    public final NumberPath<Integer> likes = createNumber("likes", Integer.class);

    public final NumberPath<Integer> userId = createNumber("userId", Integer.class);

    public QCody(String variable) {
        super(Cody.class, forVariable(variable));
    }

    public QCody(Path<? extends Cody> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCody(PathMetadata metadata) {
        super(Cody.class, metadata);
    }

}

