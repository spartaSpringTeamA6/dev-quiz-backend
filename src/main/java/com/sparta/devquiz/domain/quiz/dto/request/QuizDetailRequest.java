package com.sparta.devquiz.domain.quiz.dto.request;

import com.sparta.devquiz.domain.quiz.enums.QuizCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public class QuizDetailRequest {
    @NotNull(message = "카테고리는 필수로 입력해야 합니다.")
    @Schema(description = "랜덤 퀴즈 요청 시 선택할 퀴즈 카테고리", defaultValue = "JAVA")
    private QuizCategory category;

    @Schema(description = "퀴즈의 id", defaultValue = "1")
    private Long id;

    @Schema(description = "퀴즈의 문제", defaultValue = "Java에서 static 키워드의 주요 기능은 무엇입니까?")
    private String question;

    @Schema(description = "퀴즈의 보기", defaultValue = "1. 객체의 동적 생성\n2. 메소드 오버로딩\n3. 클래스 변수 및 메소드 생성\n4. 예외 처리\n5. 인터페이스 정의")
    private String example;

    @Schema(description = "퀴즈의 정답", defaultValue = "1")
    private String answer;

}
