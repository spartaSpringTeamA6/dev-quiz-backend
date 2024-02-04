package com.sparta.devquiz.domain.quiz.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QQuiz is a Querydsl query type for Quiz
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QQuiz extends EntityPathBase<Quiz> {

    private static final long serialVersionUID = 1853659847L;

    public static final QQuiz quiz = new QQuiz("quiz");

    public final com.sparta.devquiz.global.entity.QBaseTimeEntity _super = new com.sparta.devquiz.global.entity.QBaseTimeEntity(this);

    public final StringPath answer = createString("answer");

    public final EnumPath<com.sparta.devquiz.domain.quiz.enums.QuizCategory> category = createEnum("category", com.sparta.devquiz.domain.quiz.enums.QuizCategory.class);

    public final NumberPath<Long> correctCount = createNumber("correctCount", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final DateTimePath<java.time.LocalDateTime> deletedAt = createDateTime("deletedAt", java.time.LocalDateTime.class);

    public final StringPath example = createString("example");

    public final NumberPath<Long> failCount = createNumber("failCount", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isDeleted = createBoolean("isDeleted");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final StringPath question = createString("question");

    public final NumberPath<Long> solveCount = createNumber("solveCount", Long.class);

    public final ListPath<UserQuiz, QUserQuiz> userQuizList = this.<UserQuiz, QUserQuiz>createList("userQuizList", UserQuiz.class, QUserQuiz.class, PathInits.DIRECT2);

    public QQuiz(String variable) {
        super(Quiz.class, forVariable(variable));
    }

    public QQuiz(Path<? extends Quiz> path) {
        super(path.getType(), path.getMetadata());
    }

    public QQuiz(PathMetadata metadata) {
        super(Quiz.class, metadata);
    }

}

