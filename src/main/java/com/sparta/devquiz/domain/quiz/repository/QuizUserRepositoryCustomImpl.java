package com.sparta.devquiz.domain.quiz.repository;


import static com.sparta.devquiz.domain.quiz.entity.QQuiz.quiz;
import static com.sparta.devquiz.domain.quiz.entity.QUserQuiz.userQuiz;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.DateTemplate;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.devquiz.domain.quiz.dto.quiz.response.QuizGetByUserResponse;
import com.sparta.devquiz.domain.quiz.dto.response.QuizGetByUserResponse;
import com.sparta.devquiz.domain.quiz.dto.response.QuizSolvedGrassResponse;
import com.sparta.devquiz.domain.quiz.entity.QUserQuiz;
import com.sparta.devquiz.domain.quiz.entity.UserQuiz;
import com.sparta.devquiz.domain.quiz.enums.UserQuizStatus;
import com.sparta.devquiz.domain.user.entity.User;
import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class QuizUserRepositoryCustomImpl implements QuizUserRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<QuizGetByUserResponse> findCorrectQuizzesByUsers(User loginUser, UserQuizStatus status){
        QUserQuiz uq = userQuiz;

        List<UserQuiz> resultUserQuiz = jpaQueryFactory.select(userQuiz)
                .from(userQuiz)
                .join(quiz).on(userQuiz.quiz.id.eq(quiz.id))
                .where(userQuiz.createdAt.in(
                                JPAExpressions
                                        .select(uq.createdAt.max())
                                        .from(uq)
                                        .where(uq.user.id.eq(userQuiz.user.id)
                                                .and(uq.quiz.id.eq(userQuiz.quiz.id)))
                                        .groupBy(uq.user.id,uq.quiz.id))
                        .and(userQuiz.user.id.eq(loginUser.getId()))
                        .and(userQuiz.status.eq(status))
                        .and(quiz.isDeleted.eq(false))
                )
                .fetch();

        return resultUserQuiz.stream().map(QuizGetByUserResponse::of).toList();
    }

    @Override
    public List<QuizGetByUserResponse> findCorrectQuizzesByUsers(User loginUser){
        QUserQuiz uq = userQuiz;

        List<UserQuiz> resultUserQuiz = jpaQueryFactory.select(userQuiz)
                .from(userQuiz)
                .join(quiz).on(userQuiz.quiz.id.eq(quiz.id))
                .where(userQuiz.createdAt.in(
                                JPAExpressions
                                        .select(uq.createdAt.max())
                                        .from(uq)
                                        .where(uq.user.id.eq(userQuiz.user.id)
                                                .and(uq.quiz.id.eq(userQuiz.quiz.id)))
                                        .groupBy(uq.user.id,uq.quiz.id))
                        .and(userQuiz.user.id.eq(loginUser.getId()))
                        .and(quiz.isDeleted.eq(false))
                )
                .fetch();

        return resultUserQuiz.stream().map(QuizGetByUserResponse::of).toList();
    }

    @Override
    public List<QuizSolvedGrassResponse> findSolvedGrassByUser(User loginUser){

        DateTemplate<Date> dateExpression = Expressions.dateTemplate(Date.class, "DATE({0})", userQuiz.createdAt);

        List<Tuple> tuples = jpaQueryFactory
                .select(userQuiz.createdAt.count(), dateExpression)
                .from(userQuiz)
                .where(userQuiz.user.id.eq(loginUser.getId()))
                .groupBy(dateExpression)
                .orderBy(dateExpression.asc())
                .fetch();

        List<QuizSolvedGrassResponse> results = tuples.stream()
                .map(tuple -> new QuizSolvedGrassResponse(
                        tuple.get(0, Long.class),
                        tuple.get(1, Date.class)))
                .collect(Collectors.toList());

        return results;
    }
}
