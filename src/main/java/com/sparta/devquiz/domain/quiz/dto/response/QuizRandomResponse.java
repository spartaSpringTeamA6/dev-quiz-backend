package com.sparta.devquiz.domain.quiz.dto.response;

import com.sparta.devquiz.domain.quiz.entity.Quiz;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@Schema(description = "랜덤 퀴즈에 대한 응답 dto")
public class QuizRandomResponse {

    private Long id;

    private String question;

    private String example;

//    @ArraySchema(arraySchema = @Schema(description = "선택된 카테고리의 랜덤 퀴즈 목록"))
//    private final List<Quiz> quizzes;

//    public QuizRandomResponse(List<QuizDto> quizzes) {
//        this.quizzes = quizzes;
//    }

    public static QuizRandomResponse of(Quiz quiz) {
        return QuizRandomResponse.builder()
                .id(quiz.getId())
                .question(quiz.getQuestion())
                .example(quiz.getExample())
                .build();
    }

    public static List<QuizRandomResponse> of(List<Quiz> quizzes) {
        return quizzes.stream()
                .map(QuizRandomResponse::of)
                .toList();
    }


//    @Schema(description = "퀴즈 DTO")
//    public static class QuizDto {
//
//        @Schema(description = "생성된 퀴즈 ID", defaultValue = "1")
//        private Long id;
//
//        @Schema(description = "생성된 문제", defaultValue = "Java에서 static 키워드의 주요 기능은 무엇입니까?")
//        private String question;
//
//        @Schema(description = "생성된 보기", defaultValue = "1. 객체의 동적 생성\n2. 메소드 오버로딩\n3. 클래스 변수 및 메소드 생성\n4. 예외 처리\n5. 인터페이스 정의")
//        private String example;
//
//        public QuizDto(Long id, String question, String example) {
//            this.id = id;
//            this.question = question;
//            this.example = example;
//        }
//
//        public static QuizDto of(Long id, String question, String example) {
//            return new QuizDto(id, question, example);
//        }
//
//    }
}
