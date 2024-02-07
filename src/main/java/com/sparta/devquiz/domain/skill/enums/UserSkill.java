package com.sparta.devquiz.domain.skill.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserSkill {

    COMPUTER_SCIENCE("Computer Science"),

    JAVA("Java"),

    SPRING("Spring"),

    PYTHON("Python"),

    DJANGO("Django"),

    C("C"),

    JAVASCRIPT("JavaScript"),

    REACT("React"),

    DATABASE("Database"),

    JPA("JPA"),

    LINUX("Linux"),

    NETWORK("Network");

    private final String major;
}
