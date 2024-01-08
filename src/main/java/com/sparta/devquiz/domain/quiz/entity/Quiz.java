package com.sparta.devquiz.domain.quiz.entity;

import com.sparta.devquiz.domain.quiz.enums.QuizCategory;
import com.sparta.devquiz.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "quizzes")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Quiz extends BaseTimeEntity {

    @Id
    @Column(name = "quiz_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String question;

    @Column(nullable = false)
    private String example;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private QuizCategory category;

    @Column(nullable = false)
    private String answer;

    @Column(nullable = false)
    private Long correctCount;

    @Column(nullable = false)
    private Long failCount;

    @Column(nullable = false)
    private Long solveCount;

    @OneToMany(mappedBy = "quiz")
    private List<UserQuiz> userQuizList = new ArrayList<>();


}
