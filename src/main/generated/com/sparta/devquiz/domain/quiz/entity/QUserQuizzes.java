package com.sparta.devquiz.domain.quiz.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserQuizzes is a Querydsl query type for UserQuizzes
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserQuizzes extends EntityPathBase<UserQuizzes> {

    private static final long serialVersionUID = 678526262L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserQuizzes userQuizzes = new QUserQuizzes("userQuizzes");

    public final com.sparta.devquiz.global.entity.QBaseTimeEntity _super = new com.sparta.devquiz.global.entity.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final QQuiz quiz;

    public final NumberPath<Integer> score = createNumber("score", Integer.class);

    public final EnumPath<com.sparta.devquiz.domain.quiz.enums.UserQuizStatus> status = createEnum("status", com.sparta.devquiz.domain.quiz.enums.UserQuizStatus.class);

    public final com.sparta.devquiz.domain.user.entity.QUser user;

    public QUserQuizzes(String variable) {
        this(UserQuizzes.class, forVariable(variable), INITS);
    }

    public QUserQuizzes(Path<? extends UserQuizzes> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserQuizzes(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserQuizzes(PathMetadata metadata, PathInits inits) {
        this(UserQuizzes.class, metadata, inits);
    }

    public QUserQuizzes(Class<? extends UserQuizzes> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.quiz = inits.isInitialized("quiz") ? new QQuiz(forProperty("quiz"), inits.get("quiz")) : null;
        this.user = inits.isInitialized("user") ? new com.sparta.devquiz.domain.user.entity.QUser(forProperty("user")) : null;
    }

}

