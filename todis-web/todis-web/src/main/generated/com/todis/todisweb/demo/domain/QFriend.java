package com.todis.todisweb.demo.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QFriend is a Querydsl query type for Friend
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFriend extends EntityPathBase<Friend> {

    private static final long serialVersionUID = 1990414018L;

    public static final QFriend friend = new QFriend("friend");

    public final DateTimePath<java.sql.Timestamp> date = createDateTime("date", java.sql.Timestamp.class);

    public final NumberPath<Integer> friendId = createNumber("friendId", Integer.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final BooleanPath isAllowed = createBoolean("isAllowed");

    public final NumberPath<Integer> userId = createNumber("userId", Integer.class);

    public QFriend(String variable) {
        super(Friend.class, forVariable(variable));
    }

    public QFriend(Path<? extends Friend> path) {
        super(path.getType(), path.getMetadata());
    }

    public QFriend(PathMetadata metadata) {
        super(Friend.class, metadata);
    }

}

