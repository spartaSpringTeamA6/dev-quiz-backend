package com.sparta.devquiz.domain.team.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTeamUser is a Querydsl query type for TeamUser
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTeamUser extends EntityPathBase<TeamUser> {

    private static final long serialVersionUID = -810669246L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTeamUser teamUser = new QTeamUser("teamUser");

    public final com.sparta.devquiz.global.entity.QBaseTimeEntity _super = new com.sparta.devquiz.global.entity.QBaseTimeEntity(this);

    public final DateTimePath<java.time.LocalDateTime> acceptedAt = createDateTime("acceptedAt", java.time.LocalDateTime.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final BooleanPath isAccepted = createBoolean("isAccepted");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final QTeam team;

    public final QTeamUserId teamUserId;

    public final com.sparta.devquiz.domain.user.entity.QUser user;

    public final EnumPath<com.sparta.devquiz.domain.team.enums.TeamUserRole> userRole = createEnum("userRole", com.sparta.devquiz.domain.team.enums.TeamUserRole.class);

    public QTeamUser(String variable) {
        this(TeamUser.class, forVariable(variable), INITS);
    }

    public QTeamUser(Path<? extends TeamUser> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTeamUser(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTeamUser(PathMetadata metadata, PathInits inits) {
        this(TeamUser.class, metadata, inits);
    }

    public QTeamUser(Class<? extends TeamUser> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.team = inits.isInitialized("team") ? new QTeam(forProperty("team")) : null;
        this.teamUserId = inits.isInitialized("teamUserId") ? new QTeamUserId(forProperty("teamUserId")) : null;
        this.user = inits.isInitialized("user") ? new com.sparta.devquiz.domain.user.entity.QUser(forProperty("user")) : null;
    }

}

