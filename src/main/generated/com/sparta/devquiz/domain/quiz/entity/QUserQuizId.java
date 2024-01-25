package com.sparta.devquiz.domain.quiz.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUserQuizId is a Querydsl query type for UserQuizId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QUserQuizId extends BeanPath<UserQuizId> {

    private static final long serialVersionUID = 853170413L;

    public static final QUserQuizId userQuizId = new QUserQuizId("userQuizId");

    public final NumberPath<Long> quizId = createNumber("quizId", Long.class);

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QUserQuizId(String variable) {
        super(UserQuizId.class, forVariable(variable));
    }

    public QUserQuizId(Path<? extends UserQuizId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUserQuizId(PathMetadata metadata) {
        super(UserQuizId.class, metadata);
    }

}

