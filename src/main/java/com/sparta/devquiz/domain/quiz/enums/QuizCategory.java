package com.sparta.devquiz.domain.quiz.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum QuizCategory {

    /**
     *  CS 카테고리
     */
    COMPUTER_SCIENCE("COMPUTER_SCIENCE"),
    DESIGN_PATTERN("DESIGN_PATTERN"),

    /**
     *  백엔드 카테고리
     */
    JAVA("JAVA"),
    SPRING("SPRING"),
    Python("Python"),
    Django("Django"),
    C("C"),
    NestJS("NestJS"),

    /**
     *  프론트엔드 카테고리
     */
    JavaScript("JavaScript"),
    TypeScript("TypeScript"),
    React("React"),
    Vue("Vue"),
    NextJS("NextJS"),
    NodeJS("NodeJS"),

    /**
     *  데이터베이스 카테고리
     */
    JPA("JPA"),
    DATABASE("DATABASE"),
    MySQL("MySQL"),
    Oracle("Oracle"),

    GraphQL("GraphQL"),
    MongoDB("MongoDB"),
    FireBase("FireBase"),

    /**
     * 네트워크 OR 데브옵스
     */
    AWS("AWS"),
    Kubernetes("Kubernetes"),
    Docker("Docker"),
    Git("Git"),
    Linux("Linux");


    private final String major;

}
