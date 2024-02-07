package com.sparta.devquiz.domain.skill.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserSkill {

    COMPUTER_SCIENCE("COMPUTER_SCIENCE"),

    JAVA("JAVA"),

    SPRING("SPRING"),

    PYTHON("PYTHON"),

    DJANGO("DJANGO"),

    C("C"),

    JAVASCRIPT("JAVASCRIPT"),

    REACT("REACT"),

    JPA("JPA"),

    DATABASE("DATABASE"),

    LINUX("LINUX"),

    NETWORK("NETWORK");

    private final String major;

}
