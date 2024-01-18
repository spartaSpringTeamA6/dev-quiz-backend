package com.sparta.devquiz.domain.quiz.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "퀴즈 답안 제출 응답 DTO")
public class QuizAnswerSubmitResponse {

    @Schema(description = "퀴즈 ID", defaultValue = "1")
    private final Long id;

    @Schema(description = "제출된 답안", defaultValue = "1")
    private final String submittedAnswer;

    @Schema(description = "답안의 정확성", defaultValue = "true")
    private final boolean isCorrect;

    @Schema(description = "정답", defaultValue = "1")
    private final String correctAnswer;

    @Schema(description = "답안에 대한 결과 메시지", defaultValue = "정답입니다!")
    private final String resultMessage;

    public static QuizAnswerSubmitResponse createResponse(Long id, String submittedAnswer, boolean isCorrect, String correctAnswer, String resultMessage) {
        return new QuizAnswerSubmitResponse(id, submittedAnswer, isCorrect, correctAnswer, resultMessage);
    }

    private QuizAnswerSubmitResponse(Long id, String submittedAnswer, boolean isCorrect, String correctAnswer, String resultMessage) {
        this.id = id;
        this.submittedAnswer = submittedAnswer;
        this.isCorrect = isCorrect;
        this.correctAnswer = correctAnswer;
        this.resultMessage = resultMessage;
    }


    public static QuizAnswerSubmitResponse.Builder builder() {
        return new QuizAnswerSubmitResponse.Builder();
    }


    public static class Builder {
        private Long id;
        private String submittedAnswer;
        private boolean isCorrect;
        private String correctAnswer;
        private String resultMessage;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder submittedAnswer(String submittedAnswer) {
            this.submittedAnswer = submittedAnswer;
            return this;
        }

        public Builder isCorrect(boolean isCorrect) {
            this.isCorrect = isCorrect;
            return this;
        }

        public Builder correctAnswer(String correctAnswer) {
            this.correctAnswer = correctAnswer;
            return this;
        }

        public Builder resultMessage(String resultMessage) {
            this.resultMessage = resultMessage;
            return this;
        }

        public QuizAnswerSubmitResponse build() {
            return new QuizAnswerSubmitResponse(id, submittedAnswer, isCorrect, correctAnswer, resultMessage);
        }
    }
}
