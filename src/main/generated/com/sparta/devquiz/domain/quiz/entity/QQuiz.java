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

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QQuiz quiz = new QQuiz("quiz");

    public final com.sparta.devquiz.global.entity.QBaseTimeEntity _super = new com.sparta.devquiz.global.entity.QBaseTimeEntity(this);

    public final com.sparta.devquiz.domain.category.entity.QCategory category;

    public final NumberPath<Long> correctCount = createNumber("correctCount", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final DateTimePath<java.time.LocalDateTime> deletedAt = createDateTime("deletedAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> failCount = createNumber("failCount", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isDeleted = createBoolean("isDeleted");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final ListPath<QuizChoice, QQuizChoice> quizChoices = this.<QuizChoice, QQuizChoice>createList("quizChoices", QuizChoice.class, QQuizChoice.class, PathInits.DIRECT2);

    public final StringPath quizTitle = createString("quizTitle");

    public final NumberPath<Long> solveCount = createNumber("solveCount", Long.class);

    public QQuiz(String variable) {
        this(Quiz.class, forVariable(variable), INITS);
    }

    public QQuiz(Path<? extends Quiz> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QQuiz(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QQuiz(PathMetadata metadata, PathInits inits) {
        this(Quiz.class, metadata, inits);
    }

    public QQuiz(Class<? extends Quiz> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.category = inits.isInitialized("category") ? new com.sparta.devquiz.domain.category.entity.QCategory(forProperty("category")) : null;
    }

}

