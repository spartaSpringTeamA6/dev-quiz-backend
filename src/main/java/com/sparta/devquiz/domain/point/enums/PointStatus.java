package com.sparta.devquiz.domain.point.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PointStatus {
    SAVE("SAVE"),
    USE("USE");
    private final String status;
}
