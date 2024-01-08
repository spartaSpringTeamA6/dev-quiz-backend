package com.sparta.devquiz.domain.point.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PointContent {

    FIRST("FIRST"),
    CORRECT("CORRECT"),
    FAIL("FAIL"),
    PASS("PASS"),
    USE("USE");

    private final String status;

}