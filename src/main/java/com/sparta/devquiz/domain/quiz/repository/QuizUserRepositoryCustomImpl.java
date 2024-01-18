package com.sparta.devquiz.domain.quiz.repository;

import static com.sparta.devquiz.domain.quiz.entity.QUserQuiz.userQuiz;
import static com.sparta.devquiz.domain.quiz.entity.QQuiz.quiz;

import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.devquiz.domain.quiz.dto.response.QuizGetQuizzesByUserResponse;
import com.sparta.devquiz.domain.quiz.entity.QUserQuiz;
import com.sparta.devquiz.domain.quiz.entity.UserQuiz;
import com.sparta.devquiz.domain.quiz.enums.UserQuizStatus;
import com.sparta.devquiz.domain.user.entity.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class QuizUserRepositoryCustomImpl implements QuizUserRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<QuizGetQuizzesByUserResponse> findCorrectQuizzesByUsers(User loginUser,
            UserQuizStatus status){
        QUserQuiz uq = QUserQuiz.userQuiz;

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

        return resultUserQuiz.stream().map(QuizGetQuizzesByUserResponse::of).toList();
    }

    @Override
    public List<QuizGetQuizzesByUserResponse> findCorrectQuizzesByUsers(User loginUser){
        QUserQuiz uq = QUserQuiz.userQuiz;

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

        return resultUserQuiz.stream().map(QuizGetQuizzesByUserResponse::of).toList();
    }
}
