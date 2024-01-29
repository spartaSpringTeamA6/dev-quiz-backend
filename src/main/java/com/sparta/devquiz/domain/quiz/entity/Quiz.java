package com.sparta.devquiz.domain.quiz.entity;

import com.sparta.devquiz.domain.quiz.enums.QuizCategory;
import com.sparta.devquiz.global.entity.BaseTimeEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private Category category;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quiz_id")
    private Long id;

    @Column(nullable = false, name = "quiz_title")
    private String quizTitle;

    @OneToMany(mappedBy = "quiz", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<QuizChoice> quizChoices = new ArrayList<>();

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

    @Builder
    public Quiz(String quizTitle) {
        this.quizTitle = quizTitle;
    }

    public String findCategoryName() {
        return this.category.getCategoryTitle();
    }

    public void addCategory(Category category) {
        if (this.category == null) {
            this.category = category;
        }
    }

    public void addChoice(QuizChoice quizChoice) {
        this.quizChoices.add(quizChoice);
        if (quizChoice.getQuiz() != this) {
            quizChoice.setQuiz(this);
        }
    }

    public void addChoices(List<QuizChoice> quizChoices) {
        this.quizChoices.addAll(quizChoices);
    }


    public void updateQuizTitle(final String updateTitle) {
        this.quizTitle = updateTitle;
    }

    public void updateQuiz(QuizCategory quizCategory, String quizTitle, List<QuizChoice> quizChoices) {
        this.category = category;
        this.quizTitle = quizTitle;
        this.quizChoices = quizChoices;
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
