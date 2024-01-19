package com.sparta.devquiz.domain.team.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QTeamUserId is a Querydsl query type for TeamUserId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QTeamUserId extends BeanPath<TeamUserId> {

    private static final long serialVersionUID = -1664062467L;

    public static final QTeamUserId teamUserId = new QTeamUserId("teamUserId");

    public final NumberPath<Long> teamId = createNumber("teamId", Long.class);

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QTeamUserId(String variable) {
        super(TeamUserId.class, forVariable(variable));
    }

    public QTeamUserId(Path<? extends TeamUserId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTeamUserId(PathMetadata metadata) {
        super(TeamUserId.class, metadata);
    }

}

