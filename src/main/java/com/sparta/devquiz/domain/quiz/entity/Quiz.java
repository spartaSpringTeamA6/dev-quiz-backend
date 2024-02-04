package com.sparta.devquiz.domain.quiz.entity;

import com.sparta.devquiz.domain.quiz.enums.QuizCategory;
import com.sparta.devquiz.global.entity.BaseTimeEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
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

    @Column(nullable = false)
    private boolean isDeleted;

    @Column
    private LocalDateTime deletedAt;

    @Builder.Default
    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserQuiz> userQuizList = new ArrayList<>();

    public void updateQuiz(String question, String example, QuizCategory category, String answer){
        this.question = question;
        this.answer = answer;
        this.category = category;
        this.example = example;
    }

    public void deleteQuiz(){
        this.isDeleted = true;
        this.deletedAt = LocalDateTime.now();
    }

    public void updateCount(Long correctCount, Long failCount, Long solveCount){
        this.correctCount = correctCount;
        this.failCount = failCount;
        this.solveCount = solveCount;
    }

}
