package com.sparta.devquiz.domain.quiz.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "quiz_choice")
public class QuizChoice{

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "choice_id")
    private long choiceId;

    @Column(nullable = false, name = "choice_content")
    private String choiceContent;

    @Column(nullable = false, name = "choice_sequence")
    private int choiceSequence;

    @Column(nullable = false, name = "is_answer")
    private boolean isAnswer;

    public boolean getIsAnswer() {
        return isAnswer;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
        quiz.addChoice(this);
    }

    public void updateContent(final String updateContent) {
        this.choiceContent = updateContent;
    }

    public void updateIsAnswer(final boolean updateIsAnswer) {
        this.isAnswer = isAnswer;
    }
}
