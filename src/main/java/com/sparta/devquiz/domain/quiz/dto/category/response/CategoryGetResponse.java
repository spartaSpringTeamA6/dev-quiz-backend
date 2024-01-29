package com.sparta.devquiz.domain.quiz.dto.category.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "카테고리 조회 응답 dto")
public class CategoryGetResponse {
    @Schema(description = "카테고리 ID", defaultValue = "1")
    private final Long categoryId;

    @Schema(description = "카테고리 이름", defaultValue = "JAVA")
    private final String categoryTitle;

    @Schema(description = "카테고리 설명", defaultValue = "자바에 대한 퀴즈입니다.")
    private final String categoryDescription;
}
