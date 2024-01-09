package com.sparta.devquiz.domain.quiz.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum QuizCategory {

    DATA_STRUCTURE("COMPUTER_SCIENCE","DATA_STRUCTURE"),
    COMPUTER_ARCHITECTURE("COMPUTER_SCIENCE","GROUP_ADMIN"),
    NETWORK("COMPUTER_SCIENCE","NETWORK"),
    OPERATING_SYSTEM("COMPUTER_SCIENCE","OPERATING_SYSTEM"),
    SOFTWARE_ENGINEERING("COMPUTER_SCIENCE","SOFTWARE_ENGINEERING"),
    WEB("COMPUTER_SCIENCE","WEB"),

    DESIGN_PATTERN("DESIGN_PATTERN","DESIGN_PATTERN"),

    JAVA("LANGUAGE","JAVA"),
    SPRING("LANGUAGE","SPRING"),
    JPA("LANGUAGE","JPA"),

    DATABASE("DATABASE","DATABASE");

    private final String major;
    private final String minor;

}
