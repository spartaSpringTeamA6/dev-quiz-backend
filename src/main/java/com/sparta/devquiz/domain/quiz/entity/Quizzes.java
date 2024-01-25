package com.sparta.devquiz.domain.quiz.entity;

import com.sparta.devquiz.domain.quiz.enums.QuizCategory;
import com.sparta.devquiz.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Quizzes extends BaseTimeEntity  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private QuizCategory category;

    @Column(nullable = false)
    private String title;

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

    @OneToMany(mappedBy = "quizzes", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserQuiz> userQuizList = new ArrayList<>();

    public void updateQuizzes(QuizCategory category, String title, List<UserQuiz> userQuizList){
        this.category = category;
        this.title = title;
        this.userQuizList = userQuizList;
    }

    public void deleteQuizzes(){
        this.isDeleted = true;
        this.deletedAt = LocalDateTime.now();
    }

    public void updateCount(Long correctCount, Long failCount, Long solveCount){
        this.correctCount = correctCount;
        this.failCount = failCount;
        this.solveCount = solveCount;
    }
}
