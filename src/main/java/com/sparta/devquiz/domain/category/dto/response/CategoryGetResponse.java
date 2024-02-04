package com.sparta.devquiz.domain.category.dto.response;

import com.sparta.devquiz.domain.category.entity.Category;
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

    @Schema(description = "카테고리 별 퀴즈 개수", defaultValue = "10")
    private final Long quizCount;

    public static CategoryGetResponse of(Category category, long quizCount) {
        return CategoryGetResponse
            .builder()
            .categoryId(category.getId())
            .categoryTitle(category.getCategoryTitle())
            .categoryDescription(category.getCategoryDescription())
            .quizCount(quizCount)
            .build();
    }
}