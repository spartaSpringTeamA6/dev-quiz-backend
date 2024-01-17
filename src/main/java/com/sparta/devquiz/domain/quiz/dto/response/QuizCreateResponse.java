package com.sparta.devquiz.domain.quiz.dto.response;

import com.sparta.devquiz.domain.quiz.enums.QuizCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@Schema(description = "퀴즈 생성 응답 dto")
public class QuizCreateResponse {
    @Schema(description = "생성된 퀴즈 id", defaultValue = "1")
    private Long id;

    @Schema(description = "생성된 퀴즈의 카테고리", defaultValue = "JAVA")
    private final QuizCategory category;

    @Schema(description = "생성된 퀴즈의 문제", defaultValue = "Java에서 static 키워드의 주요 기능은 무엇입니까?")
    private final String question;

    @Schema(description = "생성된 퀴즈의 보기", defaultValue = "1번 xx,  2번 xx, 3번 xx, 4번 xx, 5번 xx")
    private final String example;

    @Schema(description = "생성된 퀴즈의 문제", defaultValue = "1")
    private final String answer;

    @Schema(description = "퀴즈 생성에 성공했음을 알리는 메시지", defaultValue = "퀴즈가 성공적으로 생성되었습니다.")
    private final String message;

    public static QuizCreateResponse of(Long id, QuizCategory category, String question, String example, String answer) {
        return QuizCreateResponse.builder()
                .id(id)
                .category(category)
                .question(question)
                .example(example)
                .answer(answer)
                .message("퀴즈가 성공적으로 생성되었습니다.")
                .build();
    }

}
