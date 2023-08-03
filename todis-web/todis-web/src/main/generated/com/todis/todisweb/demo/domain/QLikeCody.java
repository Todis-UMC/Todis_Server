package com.todis.todisweb.demo.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QLikeCody is a Querydsl query type for LikeCody
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLikeCody extends EntityPathBase<LikeCody> {

    private static final long serialVersionUID = -242222756L;

    public static final QLikeCody likeCody = new QLikeCody("likeCody");

    public final NumberPath<Integer> coordinationId = createNumber("coordinationId", Integer.class);

    public final NumberPath<Integer> keyId = createNumber("keyId", Integer.class);

    public final NumberPath<Integer> userId = createNumber("userId", Integer.class);

    public QLikeCody(String variable) {
        super(LikeCody.class, forVariable(variable));
    }

    public QLikeCody(Path<? extends LikeCody> path) {
        super(path.getType(), path.getMetadata());
    }

    public QLikeCody(PathMetadata metadata) {
        super(LikeCody.class, metadata);
    }

}

