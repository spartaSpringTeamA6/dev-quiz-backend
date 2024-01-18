package com.sparta.devquiz.domain.quiz.controller;

import com.sparta.devquiz.domain.quiz.dto.request.QuizCreateRequest;
import com.sparta.devquiz.domain.quiz.dto.request.QuizUpdateRequest;
import com.sparta.devquiz.domain.quiz.dto.response.QuizAnswerSubmitResponse;
import com.sparta.devquiz.domain.quiz.dto.response.QuizCorrectUserResponse;
import com.sparta.devquiz.domain.quiz.dto.response.QuizCreateResponse;
import com.sparta.devquiz.domain.quiz.dto.response.QuizDetailResponse;
import com.sparta.devquiz.domain.quiz.dto.response.QuizFailUserResponse;
import com.sparta.devquiz.domain.quiz.dto.response.QuizPassUserResponse;
import com.sparta.devquiz.domain.quiz.dto.response.QuizRandomResponse;
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
import org.springframework.http.HttpStatus;
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
@RequestMapping("/api/quizzes")
@Tag(name = "2. Quiz API", description = "Quiz 관련 API 입니다.")
public class QuizController {

    private final QuizService quizService;

    // 퀴즈 생성   관리자
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(operationId = "QUIZ-001", summary = "퀴즈 생성")
    @PostMapping("")
    public ResponseEntity<CommonResponseDto> createQuiz(
            @AuthUser User user,
            @RequestBody QuizCreateRequest request
    ) {
        QuizCreateResponse response = quizService.createQuiz(user, request);

        return ResponseEntity
                .status(QuizResponseCode.CREATED_QUIZ.getHttpStatus())
                .body(CommonResponseDto.of(QuizResponseCode.CREATED_QUIZ, response));
    }

    // 퀴즈 10개 랜덤 출제
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(operationId = "QUIZ-002", summary = "퀴즈 카테고리 10개 랜덤 출제")
    @GetMapping("")
    public ResponseEntity<CommonResponseDto> getRandomQuiz(
            @RequestParam QuizCategory category,
            @AuthUser User user
            ) {
        List<QuizRandomResponse> quizRandomResponseList = quizService.getRandomNonAttemptedQuizzes(category, user);

        return ResponseEntity
                .status(QuizResponseCode.OK_GET_RANDOM_QUIZZES.getHttpStatus())
                .body(CommonResponseDto.of(QuizResponseCode.OK_GET_RANDOM_QUIZZES, quizRandomResponseList));
    }


    // 퀴즈 단일 조회
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(operationId = "QUIZ-003", summary = "퀴즈 단일 조회")
    @GetMapping("/{quizId}")
    public ResponseEntity<CommonResponseDto> getQuiz(
            @PathVariable Long quizId
    ) {
        QuizDetailResponse response = quizService.getQuiz(quizId);

        return ResponseEntity
                .status(QuizResponseCode.OK_GET_QUIZ.getHttpStatus())
                .body(CommonResponseDto.of(QuizResponseCode.OK_GET_QUIZ, response));
    }

    // 퀴즈 수정   관리자
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(operationId = "QUIZ-004", summary = "퀴즈 수정: 카테고리, 문제, 예시, 정답을 수정할 수 있습니다.")
    @PatchMapping("/{quizId}")
    public ResponseEntity<CommonResponseDto> updateQuiz(
            @AuthUser User user,
            @PathVariable Long quizId,
            @RequestBody QuizUpdateRequest quizUpdateRequest
    ) {
        quizService.updateQuiz(quizId, quizUpdateRequest);
        return ResponseEntity
                .status(QuizResponseCode.OK_UPDATE_QUIZ.getHttpStatus())
                .body(CommonResponseDto.of(QuizResponseCode.OK_UPDATE_QUIZ));
    }

    // 퀴즈 삭제   관리자
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(operationId = "QUIZ-005", summary = "퀴즈 삭제")
    @DeleteMapping("/{quizId}")
    public ResponseEntity<CommonResponseDto> deleteQuiz(
            @PathVariable Long quizId
    ) {
        quizService.deleteQuiz(quizId);
        return ResponseEntity
                .status(QuizResponseCode.OK_DELETE_QUIZ.getHttpStatus())
                .body(CommonResponseDto.of(QuizResponseCode.OK_DELETE_QUIZ));
    }

    // 퀴즈 정답 제출
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(operationId = "QUIZ-006", summary = "퀴즈 정답 제출")
    @PostMapping("/{quizId}")
    public ResponseEntity<CommonResponseDto> submitQuiz(
            @PathVariable Long quizId,
            @RequestBody String submittedAnswer,
            @AuthUser User user
    ) {
        QuizAnswerSubmitResponse response = quizService.submitQuizAnswer(quizId, submittedAnswer, user); // 퀴즈 서비스 호출 결과 저장
        return ResponseEntity
                .status(QuizResponseCode.OK_SUBMIT_QUIZ_ANSWER.getHttpStatus())
                .body(CommonResponseDto.of(QuizResponseCode.OK_SUBMIT_QUIZ_ANSWER, response)); // 결과 반환에 response 추가
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(operationId = "QUIZ-007", summary = "사용자가 맞춘 퀴즈 조회")
    @GetMapping("/correct")
    public ResponseEntity<CommonResponseDto> getCorrectQuizzes(
            @AuthUser User user
    ) {
        List<QuizCorrectUserResponse> correctQuizzes = quizService.getCorrectQuizzesForUser(user);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(CommonResponseDto.of(QuizResponseCode.OK_GET_CORRECT_QUIZZES, correctQuizzes));
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(operationId = "QUIZ-008", summary = "사용자가 틀린 퀴즈 조회")
    @GetMapping("/fail")
    public ResponseEntity<CommonResponseDto> getFailQuizzes(
            @AuthUser User user
    ) {
        List<QuizFailUserResponse> failQuizzes = quizService.getFailQuizzesForUser(user);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(CommonResponseDto.of(QuizResponseCode.OK_GET_FAIL_QUIZZES, failQuizzes));
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(operationId = "QUIZ-009", summary = "사용자가 넘긴 퀴즈 조회")
    @GetMapping("/pass")
    public ResponseEntity<CommonResponseDto> getPassQuizzes(
            @AuthUser User user
    ) {
        List<QuizPassUserResponse> passQuizzes = quizService.getPassQuizzesForUser(user);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(CommonResponseDto.of(QuizResponseCode.OK_GET_PASS_QUIZZES, passQuizzes));
    }
}
