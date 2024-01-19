package com.sparta.devquiz.domain.coin.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCoin is a Querydsl query type for Coin
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCoin extends EntityPathBase<Coin> {

    private static final long serialVersionUID = -818726593L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCoin coin = new QCoin("coin");

    public final com.sparta.devquiz.global.entity.QBaseTimeEntity _super = new com.sparta.devquiz.global.entity.QBaseTimeEntity(this);

    public final NumberPath<Integer> coins = createNumber("coins", Integer.class);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final StringPath status = createString("status");

    public final com.sparta.devquiz.domain.user.entity.QUser user;

    public QCoin(String variable) {
        this(Coin.class, forVariable(variable), INITS);
    }

    public QCoin(Path<? extends Coin> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCoin(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCoin(PathMetadata metadata, PathInits inits) {
        this(Coin.class, metadata, inits);
    }

    public QCoin(Class<? extends Coin> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new com.sparta.devquiz.domain.user.entity.QUser(forProperty("user")) : null;
    }

}

