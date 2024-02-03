package com.sparta.devquiz.domain.quiz.dto.response;

import com.sparta.devquiz.domain.quiz.entity.Quiz;
import com.sparta.devquiz.domain.quiz.enums.UserQuizStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "퀴즈 답안 제출 응답 dto")
public class QuizResultResponse {

    @Schema(description = "퀴즈 ID", defaultValue = "1")
    private final Long quizId;
    
    @Schema(description = "제출된 보기 번호", defaultValue = "1")
    private final int choiceSequence;

    @Schema(description = "제출된 보기 내용", defaultValue = "1")
    private final String choiceContent;

    @Schema(description = "상태", defaultValue = "CORRECT")
    private final String status;

    @Schema(description = "정답", defaultValue = "1")
    private final Boolean correctAnswer;

    @Schema(description = "답안에 대한 결과 메시지", defaultValue = "정답입니다!")
    private final String resultMessage;

    public static QuizResultResponse of(Quiz quiz, int choiceSequence, String choiceContent, UserQuizStatus userQuizStatus, Boolean correctAnswer ) {
        return QuizResultResponse.builder()
                .quizId(quiz.getId())
                .choiceSequence(choiceSequence)
                .choiceContent(choiceContent)
                .correctAnswer(correctAnswer)
                .status(userQuizStatus.getStatus())
                .resultMessage(userQuizStatus.getMessage())
                .build();
    }
}
