package com.sparta.devquiz.domain.comment.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCommentLikeId is a Querydsl query type for CommentLikeId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QCommentLikeId extends BeanPath<CommentLikeId> {

    private static final long serialVersionUID = -1282682155L;

    public static final QCommentLikeId commentLikeId = new QCommentLikeId("commentLikeId");

    public final NumberPath<Long> commentId = createNumber("commentId", Long.class);

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QCommentLikeId(String variable) {
        super(CommentLikeId.class, forVariable(variable));
    }

    public QCommentLikeId(Path<? extends CommentLikeId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCommentLikeId(PathMetadata metadata) {
        super(CommentLikeId.class, metadata);
    }

}

