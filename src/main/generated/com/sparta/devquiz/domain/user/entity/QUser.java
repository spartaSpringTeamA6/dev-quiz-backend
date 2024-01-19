package com.sparta.devquiz.domain.user.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = 1165995187L;

    public static final QUser user = new QUser("user");

    public final com.sparta.devquiz.global.entity.QBaseTimeEntity _super = new com.sparta.devquiz.global.entity.QBaseTimeEntity(this);

    public final ListPath<com.sparta.devquiz.domain.board.entity.Board, com.sparta.devquiz.domain.board.entity.QBoard> boardList = this.<com.sparta.devquiz.domain.board.entity.Board, com.sparta.devquiz.domain.board.entity.QBoard>createList("boardList", com.sparta.devquiz.domain.board.entity.Board.class, com.sparta.devquiz.domain.board.entity.QBoard.class, PathInits.DIRECT2);

    public final ListPath<com.sparta.devquiz.domain.coin.entity.Coin, com.sparta.devquiz.domain.coin.entity.QCoin> coinList = this.<com.sparta.devquiz.domain.coin.entity.Coin, com.sparta.devquiz.domain.coin.entity.QCoin>createList("coinList", com.sparta.devquiz.domain.coin.entity.Coin.class, com.sparta.devquiz.domain.coin.entity.QCoin.class, PathInits.DIRECT2);

    public final ListPath<com.sparta.devquiz.domain.comment.entity.Comment, com.sparta.devquiz.domain.comment.entity.QComment> commentList = this.<com.sparta.devquiz.domain.comment.entity.Comment, com.sparta.devquiz.domain.comment.entity.QComment>createList("commentList", com.sparta.devquiz.domain.comment.entity.Comment.class, com.sparta.devquiz.domain.comment.entity.QComment.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final DateTimePath<java.time.LocalDateTime> deletedAt = createDateTime("deletedAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isDeleted = createBoolean("isDeleted");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final StringPath oauthId = createString("oauthId");

    public final EnumPath<com.sparta.devquiz.domain.user.enums.OauthType> oauthType = createEnum("oauthType", com.sparta.devquiz.domain.user.enums.OauthType.class);

    public final StringPath password = createString("password");

    public final EnumPath<com.sparta.devquiz.domain.user.enums.UserRole> role = createEnum("role", com.sparta.devquiz.domain.user.enums.UserRole.class);

    public final ListPath<Skill, QSkill> skillList = this.<Skill, QSkill>createList("skillList", Skill.class, QSkill.class, PathInits.DIRECT2);

    public final ListPath<com.sparta.devquiz.domain.team.entity.TeamUser, com.sparta.devquiz.domain.team.entity.QTeamUser> teamUserList = this.<com.sparta.devquiz.domain.team.entity.TeamUser, com.sparta.devquiz.domain.team.entity.QTeamUser>createList("teamUserList", com.sparta.devquiz.domain.team.entity.TeamUser.class, com.sparta.devquiz.domain.team.entity.QTeamUser.class, PathInits.DIRECT2);

    public final NumberPath<Integer> totalCoin = createNumber("totalCoin", Integer.class);

    public final StringPath username = createString("username");

    public final ListPath<com.sparta.devquiz.domain.quiz.entity.UserQuiz, com.sparta.devquiz.domain.quiz.entity.QUserQuiz> userQuizList = this.<com.sparta.devquiz.domain.quiz.entity.UserQuiz, com.sparta.devquiz.domain.quiz.entity.QUserQuiz>createList("userQuizList", com.sparta.devquiz.domain.quiz.entity.UserQuiz.class, com.sparta.devquiz.domain.quiz.entity.QUserQuiz.class, PathInits.DIRECT2);

    public final NumberPath<Integer> weekScore = createNumber("weekScore", Integer.class);

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}

