package com.sparta.devquiz.domain.user.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserSkill {

    JAVA("BACKEND","JAVA"),
    SPRING("BACKEND","SPRING"),
    PYTHON("BACKEND","PYTHON"),
    DJANGO("BACKEND","DJANGO"),
    C("BACKEND","C"),
    NODEJS("BACKEND","NODEJS"),

    SQL("DATABASE","SQL"),
    GRAPHQL("DATABASE","GRAPHQL"),
    MONGODB("DATABASE","MONGODB"),
    FIREBASE("DATABASE","FIREBASE"),

    AWS("INFRA","AWS"),
    KUBERNETES("INFRA","KUBERNETES"),
    DOCKER("INFRA","DOCKER"),
    GIT("INFRA","GIT"),
    LINUX("INFRA","LINUX"),

    JAVASCRIPT("FRONT","JAVASCRIPT"),
    TYPESCRIPT("FRONT","TYPESCRIPT"),
    REACT("FRONT","REACT"),
    VUE("FRONT","VUE");

    private final String major;
    private final String minor;

}
