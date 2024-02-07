package com.sparta.devquiz.domain.category.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "카테고리 생성 요청 dto")
public class CategoryCreateRequest {

        @Schema(description = "카테고리 이름", defaultValue = "JAVA")
        private String categoryTitle;

        @Schema(description = "카테고리 설명", defaultValue = "자바에 대한 퀴즈입니다.")
        private String categoryDescription;

}
