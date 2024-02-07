package com.sparta.devquiz.domain.quiz.controller;

import com.sparta.devquiz.domain.quiz.dto.request.QuizAnswerSubmitRequest;
import com.sparta.devquiz.domain.quiz.dto.request.QuizCreateRequest;
import com.sparta.devquiz.domain.quiz.dto.request.QuizUpdateRequest;
import com.sparta.devquiz.domain.quiz.dto.response.QuizDetailInfoResponse;
import com.sparta.devquiz.domain.quiz.dto.response.QuizPassResponse;
import com.sparta.devquiz.domain.quiz.dto.response.QuizQueryResponse;
import com.sparta.devquiz.domain.quiz.dto.response.QuizRandomResponse;
import com.sparta.devquiz.domain.quiz.dto.response.QuizResultResponse;
import com.sparta.devquiz.domain.category.enums.QuizCategory;
import com.sparta.devquiz.domain.quiz.response.QuizResponseCode;
import com.sparta.devquiz.domain.quiz.service.QuizService;
import com.sparta.devquiz.domain.user.entity.User;
import com.sparta.devquiz.global.annotation.AuthUser;
import com.sparta.devquiz.global.response.CommonResponseDto;
import io.swagger.v3.oas.annotations.Operation;
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
@RequestMapping("/api")
@Tag(name = "2. Quiz API", description = "Quiz 관련 API 입니다.")
public class QuizController {

    private final QuizService quizService;

    @Operation(operationId = "QUIZ-001", summary = "카테고리 별 퀴즈 랜덤 출제")
    @GetMapping("/quizzes")
    public ResponseEntity<CommonResponseDto> getRandomQuiz(
            @RequestParam QuizCategory category,
            @AuthUser User user
            ) {
        List<QuizRandomResponse> quizRandomResponseList = quizService.getRandomNonAttemptedQuizzes(category, user);

        return ResponseEntity.status(QuizResponseCode.OK_GET_RANDOM_QUIZZES.getHttpStatus())
                .body(CommonResponseDto.of(QuizResponseCode.OK_GET_RANDOM_QUIZZES, quizRandomResponseList));
    }

    @Operation(operationId = "QUIZ-002", summary = "퀴즈 단일 조회")
    @GetMapping("/quizzes/{quizId}")
    public ResponseEntity<CommonResponseDto> getQuiz(
            @PathVariable Long quizId
    ) {
        QuizDetailInfoResponse response = quizService.getQuiz(quizId);

        return ResponseEntity.status(QuizResponseCode.OK_GET_QUIZ.getHttpStatus())
                .body(CommonResponseDto.of(QuizResponseCode.OK_GET_QUIZ, response));
    }

    @Operation(operationId = "QUIZ-003", summary = "퀴즈 정답 제출")
    @PostMapping("/quizzes/{quizId}")
    public ResponseEntity<CommonResponseDto> submitQuiz(
            @PathVariable Long quizId,
            @RequestBody QuizAnswerSubmitRequest request,
            @AuthUser User user
    ) {
        QuizResultResponse response = quizService.submitQuizAnswer(quizId, user, request);
        return ResponseEntity.status(QuizResponseCode.OK_SUBMIT_QUIZ_ANSWER.getHttpStatus())
                .body(CommonResponseDto.of(QuizResponseCode.OK_SUBMIT_QUIZ_ANSWER, response));
    }

    @Operation(operationId = "QUIZ-004", summary = "퀴즈 패스")
    @PostMapping("/quizzes/{quizId}/pass")
    public ResponseEntity<CommonResponseDto> passQuiz(
            @PathVariable Long quizId,
            @AuthUser User user
    ) {
        QuizPassResponse response = quizService.passQuiz(quizId, user);
        return ResponseEntity.status(QuizResponseCode.OK_SUBMIT_QUIZ_ANSWER.getHttpStatus())
                .body(CommonResponseDto.of(QuizResponseCode.OK_SUBMIT_QUIZ_ANSWER, response));
    }

    @Operation(operationId = "ADMIN-001", summary = "각 카테고리 별 퀴즈 전체 조회")
    @GetMapping("/admin/quizzes")
    public ResponseEntity<CommonResponseDto> getQuizzesByCategory(
            @RequestParam QuizCategory category,
            @AuthUser User user
    ) {
        List<QuizQueryResponse> quizQueryResponseList = quizService.getQuizzesByCategory(category, user);
        return ResponseEntity
                .status(QuizResponseCode.OK_GET_TOTAL_QUIZZES.getHttpStatus())
                .body(CommonResponseDto.of(QuizResponseCode.OK_GET_TOTAL_QUIZZES, quizQueryResponseList));
    }

    @Operation(operationId = "ADMIN-002", summary = "관리자 퀴즈 생성")
    @PostMapping("/admin/quizzes")
    public ResponseEntity<CommonResponseDto> createQuiz(
            @AuthUser User user,
            @RequestParam QuizCategory category,
            @RequestBody QuizCreateRequest request
    ) {
        quizService.createQuiz(request, user, category);

        return ResponseEntity.status(QuizResponseCode.CREATED_QUIZ.getHttpStatus())
                .body(CommonResponseDto.of(QuizResponseCode.CREATED_QUIZ));
    }

    @Operation(operationId = "ADMIN-003", summary = "퀴즈 수정")
    @PatchMapping("/admin/quizzes/{quizId}")
    public ResponseEntity<CommonResponseDto> updateQuiz(
            @AuthUser User user,
            @PathVariable Long quizId,
            @RequestBody QuizUpdateRequest quizUpdateRequest
    ) {
        quizService.updateQuiz(quizUpdateRequest, user, quizId);
        return ResponseEntity.status(QuizResponseCode.OK_UPDATE_QUIZ.getHttpStatus())
                .body(CommonResponseDto.of(QuizResponseCode.OK_UPDATE_QUIZ));
    }

    @Operation(operationId = "ADMIN-004", summary = "퀴즈 삭제")
    @DeleteMapping("/admin/quizzes/{quizId}")
    public ResponseEntity<CommonResponseDto> deleteQuiz(
            @AuthUser User user,
            @PathVariable Long quizId
    ) {
        quizService.deleteQuiz(quizId, user);
        return ResponseEntity.status(QuizResponseCode.OK_DELETE_QUIZ.getHttpStatus())
                .body(CommonResponseDto.of(QuizResponseCode.OK_DELETE_QUIZ));
    }
}