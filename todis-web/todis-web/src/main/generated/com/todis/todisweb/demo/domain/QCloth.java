package com.todis.todisweb.demo.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCloth is a Querydsl query type for Cloth
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCloth extends EntityPathBase<Cloth> {

    private static final long serialVersionUID = 1585284470L;

    public static final QCloth cloth = new QCloth("cloth");

    public final StringPath color = createString("color");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath material = createString("material");

    public final StringPath name = createString("name");

    public final StringPath season = createString("season");

    public final StringPath size = createString("size");

    public final StringPath type = createString("type");

    public QCloth(String variable) {
        super(Cloth.class, forVariable(variable));
    }

    public QCloth(Path<? extends Cloth> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCloth(PathMetadata metadata) {
        super(Cloth.class, metadata);
    }

}

