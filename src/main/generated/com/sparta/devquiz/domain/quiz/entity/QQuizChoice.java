package com.sparta.devquiz.domain.quiz.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QQuizChoice is a Querydsl query type for QuizChoice
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QQuizChoice extends EntityPathBase<QuizChoice> {

    private static final long serialVersionUID = -1201966264L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QQuizChoice quizChoice = new QQuizChoice("quizChoice");

    public final StringPath choiceContent = createString("choiceContent");

    public final NumberPath<Long> choiceId = createNumber("choiceId", Long.class);

    public final BooleanPath isAnswer = createBoolean("isAnswer");

    public final QQuiz quiz;

    public QQuizChoice(String variable) {
        this(QuizChoice.class, forVariable(variable), INITS);
    }

    public QQuizChoice(Path<? extends QuizChoice> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QQuizChoice(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QQuizChoice(PathMetadata metadata, PathInits inits) {
        this(QuizChoice.class, metadata, inits);
    }

    public QQuizChoice(Class<? extends QuizChoice> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.quiz = inits.isInitialized("quiz") ? new QQuiz(forProperty("quiz"), inits.get("quiz")) : null;
    }

}

