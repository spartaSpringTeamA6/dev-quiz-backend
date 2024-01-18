package com.sparta.devquiz.domain.quiz.dto.response;

import com.sparta.devquiz.domain.quiz.entity.Quiz;
import com.sparta.devquiz.domain.quiz.enums.UserQuizStatus;
import com.sparta.devquiz.domain.team.dto.response.TeamCreateResponse;
import com.sparta.devquiz.domain.team.entity.Team;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "퀴즈 답안 제출 응답 dto")
public class QuizAnswerSubmitResponse {

    @Schema(description = "퀴즈 ID", defaultValue = "1")
    private final Long id;

    @Schema(description = "제출된 답안", defaultValue = "1")
    private final String submittedAnswer;

    @Schema(description = "상태", defaultValue = "CORRECT")
    private final String status;

    @Schema(description = "정답", defaultValue = "1")
    private final String correctAnswer;

    @Schema(description = "답안에 대한 결과 메시지", defaultValue = "정답입니다!")
    private final String resultMessage;

    public static QuizAnswerSubmitResponse of(Quiz quiz, String submittedAnswer, UserQuizStatus userQuizStatus ) {
        return QuizAnswerSubmitResponse.builder()
                .id(quiz.getId())
                .submittedAnswer(submittedAnswer)
                .correctAnswer(quiz.getAnswer())
                .status(userQuizStatus.getStatus())
                .resultMessage(userQuizStatus.getMessage())
                .build();
    }

}
