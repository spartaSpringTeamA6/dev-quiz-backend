package com.sparta.devquiz.domain.quiz.entity;

import com.sparta.devquiz.domain.quiz.enums.QuizCategory;
import com.sparta.devquiz.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Quiz extends BaseTimeEntity {

    @Id
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

    @Builder.Default
    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserQuiz> userQuizList = new ArrayList<>();

    public void updateQuiz(String question, String example, QuizCategory category, String answer){
        this.question = question;
        this.answer = answer;
        this.category = category;
        this.example = example;
    }

    public void updateCount(Long correctCount, Long failCount, Long solveCount){
        this.correctCount = correctCount;
        this.failCount = failCount;
        this.solveCount = solveCount;
    }

}
