package com.sparta.devquiz.domain.quiz.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserQuiz is a Querydsl query type for UserQuiz
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserQuiz extends EntityPathBase<UserQuiz> {

    private static final long serialVersionUID = -2077322190L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserQuiz userQuiz = new QUserQuiz("userQuiz");

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

    public QUserQuiz(String variable) {
        this(UserQuiz.class, forVariable(variable), INITS);
    }

    public QUserQuiz(Path<? extends UserQuiz> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserQuiz(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserQuiz(PathMetadata metadata, PathInits inits) {
        this(UserQuiz.class, metadata, inits);
    }

    public QUserQuiz(Class<? extends UserQuiz> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.quiz = inits.isInitialized("quiz") ? new QQuiz(forProperty("quiz")) : null;
        this.user = inits.isInitialized("user") ? new com.sparta.devquiz.domain.user.entity.QUser(forProperty("user")) : null;
    }

}

