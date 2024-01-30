package com.sparta.devquiz.domain.quiz.controller;

import com.sparta.devquiz.domain.quiz.dto.quiz.request.QuizAnswerSubmitRequest;
import com.sparta.devquiz.domain.quiz.dto.quiz.request.QuizCreateRequest;
import com.sparta.devquiz.domain.quiz.dto.quiz.request.QuizUpdateRequest;
import com.sparta.devquiz.domain.quiz.dto.quiz.response.QuizPassResponse;
import com.sparta.devquiz.domain.quiz.dto.quiz.response.QuizResultResponse;
import com.sparta.devquiz.domain.quiz.dto.quiz.response.QuizDetailInfoResponse;
import com.sparta.devquiz.domain.quiz.dto.quiz.response.QuizRandomResponse;
import com.sparta.devquiz.domain.quiz.enums.QuizCategory;
import com.sparta.devquiz.domain.quiz.response.QuizResponseCode;
import com.sparta.devquiz.domain.quiz.service.QuizService;
import com.sparta.devquiz.domain.user.entity.User;
import com.sparta.devquiz.global.annotation.AuthUser;
import com.sparta.devquiz.global.response.CommonResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories/{categoryId}/quizzes")
@Tag(name = "2. Quiz API", description = "Quiz 관련 API 입니다.")
public class QuizController {

    private final QuizService quizService;

    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(operationId = "QUIZ-001", summary = "퀴즈 생성")
    @PostMapping
    public ResponseEntity<CommonResponseDto> createQuiz(
            @AuthUser User user,
            @RequestParam Long categoryId,
            @RequestBody QuizCreateRequest request
    ) {
        quizService.createQuiz(request, user, categoryId);

        return ResponseEntity.status(QuizResponseCode.CREATED_QUIZ.getHttpStatus())
                .body(CommonResponseDto.of(QuizResponseCode.CREATED_QUIZ));
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(operationId = "QUIZ-002", summary = "퀴즈 카테고리 10개 랜덤 출제")
    @GetMapping("/random")
    public ResponseEntity<CommonResponseDto> getRandomQuiz(
            @RequestParam QuizCategory category,
            @AuthUser User user
            ) {
        List<QuizRandomResponse> quizRandomResponseList = quizService.getRandomNonAttemptedQuizzes(category, user);

        return ResponseEntity.status(QuizResponseCode.OK_GET_RANDOM_QUIZZES.getHttpStatus())
                .body(CommonResponseDto.of(QuizResponseCode.OK_GET_RANDOM_QUIZZES, quizRandomResponseList));
    }

    @Operation(operationId = "QUIZ-003", summary = "퀴즈 단일 조회")
    @GetMapping("/{quizId}")
    public ResponseEntity<CommonResponseDto> getQuiz(
            @PathVariable Long quizId
    ) {
        QuizDetailInfoResponse response = quizService.getQuiz(quizId);

        return ResponseEntity.status(QuizResponseCode.OK_GET_QUIZ.getHttpStatus())
                .body(CommonResponseDto.of(QuizResponseCode.OK_GET_QUIZ, response));
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(operationId = "QUIZ-004", summary = "퀴즈 수정: 카테고리, 문제, 예시, 정답을 수정할 수 있습니다.")
    @PatchMapping("/{quizId}")
    public ResponseEntity<CommonResponseDto> updateQuiz(
            @AuthUser User user,
            @PathVariable Long quizId,
            @RequestParam Long categoryId,
            @RequestBody QuizUpdateRequest quizUpdateRequest
    ) {
        quizService.updateQuiz(quizUpdateRequest, user, quizId, categoryId);
        return ResponseEntity.status(QuizResponseCode.OK_UPDATE_QUIZ.getHttpStatus())
                .body(CommonResponseDto.of(QuizResponseCode.OK_UPDATE_QUIZ));
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(operationId = "QUIZ-005", summary = "퀴즈 삭제")
    @DeleteMapping("/{quizId}")
    public ResponseEntity<CommonResponseDto> deleteQuiz(
            @AuthUser User user,
            @PathVariable Long quizId
    ) {
        quizService.deleteQuiz(quizId, user);
        return ResponseEntity.status(QuizResponseCode.OK_DELETE_QUIZ.getHttpStatus())
                .body(CommonResponseDto.of(QuizResponseCode.OK_DELETE_QUIZ));
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(operationId = "QUIZ-006", summary = "퀴즈 정답 제출")
    @PostMapping("/{quizId}")
    public ResponseEntity<CommonResponseDto> submitQuiz(
            @PathVariable Long quizId,
            @RequestBody QuizAnswerSubmitRequest request,
            @AuthUser User user
    ) {
        QuizResultResponse response = quizService.submitQuizAnswer(quizId, user, request);
        return ResponseEntity.status(QuizResponseCode.OK_SUBMIT_QUIZ_ANSWER.getHttpStatus())
                .body(CommonResponseDto.of(QuizResponseCode.OK_SUBMIT_QUIZ_ANSWER, response));
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(operationId = "QUIZ-007", summary = "각 카테고리 별 퀴즈 전체 조회")
    @GetMapping
    public ResponseEntity<CommonResponseDto> getQuizzes(
            @PathVariable Long categoryId,
            @RequestParam int page,
            @AuthUser User user
    ){}
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(operationId = "QUIZ-008", summary = "퀴즈 패스")
    @PostMapping("/{quizId}/pass")
       public ResponseEntity<CommonResponseDto> passQuiz(
            @PathVariable Long quizId,
            @RequestBody QuizAnswerSubmitRequest request,
            @AuthUser User user

    ) {
        QuizPassResponse response = quizService.passQuiz(quizId, user, request);
        return ResponseEntity.status(QuizResponseCode.OK_SUBMIT_QUIZ_ANSWER.getHttpStatus())
                .body(CommonResponseDto.of(QuizResponseCode.OK_SUBMIT_QUIZ_ANSWER, response));
    }
}