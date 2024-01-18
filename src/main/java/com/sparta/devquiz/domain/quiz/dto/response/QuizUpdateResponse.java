package com.sparta.devquiz.domain.quiz.dto.response;

import com.sparta.devquiz.domain.quiz.enums.QuizCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "퀴즈 업데이트 응답 DTO")
public class QuizUpdateResponse {

    @Schema(description = "업데이트된 퀴즈의 ID", example = "1")
    private final Long id;

    @Schema(description = "퀴즈의 카테고리", example = "JAVA")
    private final QuizCategory category;

    @Schema(description = "업데이트된 퀴즈의 문제", example = "Java에서 static 키워드의 주요 기능은 무엇입니까?")
    private final String question;

    @Schema(description = "업데이트된 퀴즈의 보기", example = "1. 객체의 동적 생성\n2. 메소드 오버로딩\n3. 클래스 변수 및 메소드 생성\n4. 예외 처리\n5. 인터페이스 정의")
    private final String example;

    @Schema(description = "퀴즈의 정답", example = "3")
    private final String answer;

    @Schema(description = "업데이트 결과 메시지", example = "퀴즈 업데이트 성공")
    private final String message;

    public static QuizUpdateResponse of(Long id, QuizCategory category, String question, String example, String answer) {
        return QuizUpdateResponse.builder()
                .id(id)
                .category(category)
                .question(question)
                .example(example)
                .answer(answer)
                .message("퀴즈 업데이트 성공")
                .build();
    }
}