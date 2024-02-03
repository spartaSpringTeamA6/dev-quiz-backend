package com.sparta.devquiz.domain.category.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCategory is a Querydsl query type for Category
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCategory extends EntityPathBase<Category> {

    private static final long serialVersionUID = -1196149479L;

    public static final QCategory category = new QCategory("category");

    public final com.sparta.devquiz.global.entity.QBaseTimeEntity _super = new com.sparta.devquiz.global.entity.QBaseTimeEntity(this);

    public final StringPath categoryDescription = createString("categoryDescription");

    public final StringPath categoryTitle = createString("categoryTitle");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final ListPath<com.sparta.devquiz.domain.quiz.entity.Quiz, com.sparta.devquiz.domain.quiz.entity.QQuiz> quizzes = this.<com.sparta.devquiz.domain.quiz.entity.Quiz, com.sparta.devquiz.domain.quiz.entity.QQuiz>createList("quizzes", com.sparta.devquiz.domain.quiz.entity.Quiz.class, com.sparta.devquiz.domain.quiz.entity.QQuiz.class, PathInits.DIRECT2);

    public QCategory(String variable) {
        super(Category.class, forVariable(variable));
    }

    public QCategory(Path<? extends Category> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCategory(PathMetadata metadata) {
        super(Category.class, metadata);
    }

}

