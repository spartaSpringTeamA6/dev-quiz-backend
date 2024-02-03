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
    PYTHON("PYTHON"),
    DJANGO("DJANGO"),
    C("C"),
    NEST_JS("NEST_JS"),

    /**
     *  프론트엔드 카테고리
     */
    JAVASCRIPT("JAVASCRIPT"),
    TYPESCRIPT("TYPESCRIPT"),
    REACT("REACT"),
    VUE("VUE"),
    NEXT_JS("NEXT_JS"),
    NODE_JS("NODE_JS"),

    /**
     *  데이터베이스 카테고리
     */
    JPA("JPA"),
    DATABASE("DATABASE"),
    MY_SQL("MY_SQL"),
    ORACLE("ORACLE"),

    GRAPH_QL("GRAPH_QL"),
    MONGO_DB("MONGO_DB"),
    FIREBASE("FIREBASE"),

    /**
     * 네트워크 OR 데브옵스
     */
    AWS("AWS"),
    KUBERNETES("KUBERNETES"),
    DOCKER("DOCKER"),
    GIT("GIT"),
    LINUX("LINUX");


    private final String major;

    public String get() {
        return major;
    }
}
