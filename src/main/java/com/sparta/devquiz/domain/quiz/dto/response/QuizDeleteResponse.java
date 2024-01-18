package com.sparta.devquiz.domain.quiz.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "퀴즈 삭제 응답 DTO")
public class QuizDeleteResponse {

    @Schema(description = "삭제된 퀴즈의 ID")
    private final Long id;

    @Schema(description = "삭제 요청 처리 결과 메시지")
    private final String message;

    public static QuizDeleteResponse of(Long id, String message) {
        return QuizDeleteResponse.builder()
                .id(id)
                .message("퀴즈 삭제 성공")
                .build();
    }


}
