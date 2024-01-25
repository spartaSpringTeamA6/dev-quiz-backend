package com.sparta.devquiz.domain.quiz.entity;

import com.sparta.devquiz.global.entity.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;

public class QuizTexts extends BaseTimeEntity {

    @OneToMany
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    @Column
    private int num;

    @Column(nullable = false)
    private String content;

    @Column
    private boolean isAnswer;

}
