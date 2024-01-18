package com.sparta.devquiz.domain.quiz.service;

import com.sparta.devquiz.domain.quiz.dto.request.QuizCreateRequest;
import com.sparta.devquiz.domain.quiz.dto.request.QuizUpdateRequest;
import com.sparta.devquiz.domain.quiz.dto.response.QuizAnswerSubmitResponse;
import com.sparta.devquiz.domain.quiz.dto.response.QuizCorrectUserResponse;
import com.sparta.devquiz.domain.quiz.dto.response.QuizCreateResponse;
import com.sparta.devquiz.domain.quiz.dto.response.QuizDeleteResponse;
import com.sparta.devquiz.domain.quiz.dto.response.QuizDetailResponse;
import com.sparta.devquiz.domain.quiz.dto.response.QuizFailUserResponse;
import com.sparta.devquiz.domain.quiz.dto.response.QuizPassUserResponse;
import com.sparta.devquiz.domain.quiz.dto.response.QuizRandomResponse;
import com.sparta.devquiz.domain.quiz.dto.response.QuizUpdateResponse;
import com.sparta.devquiz.domain.quiz.entity.Quiz;
import com.sparta.devquiz.domain.quiz.entity.UserQuiz;
import com.sparta.devquiz.domain.quiz.enums.QuizCategory;
import com.sparta.devquiz.domain.quiz.enums.UserQuizStatus;
import com.sparta.devquiz.domain.quiz.exception.QuizCustomException;
import com.sparta.devquiz.domain.quiz.exception.QuizExceptionCode;
import com.sparta.devquiz.domain.quiz.repository.QuizRepository;
import com.sparta.devquiz.domain.quiz.repository.QuizUserRepository;
import com.sparta.devquiz.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuizService {

    private final QuizRepository quizRepository;
    private final QuizUserRepository quizUserRepository;

    @Transactional
    public QuizCreateResponse createQuiz(User user, QuizCreateRequest createRequest) {

        Quiz quiz = Quiz.builder()
                .category(createRequest.getCategory())
                .question(createRequest.getQuestion())
                .example(createRequest.getExample())
                .answer(createRequest.getAnswer())
                .correctCount(0L)
                .failCount(0L)
                .solveCount(0L)
                .build();

        Quiz savedQuiz = quizRepository.save(quiz);

        return QuizCreateResponse.builder()
                .id(savedQuiz.getId())
                .category(savedQuiz.getCategory())
                .question(savedQuiz.getQuestion())
                .example(savedQuiz.getExample())
                .answer(savedQuiz.getAnswer())
                .message("퀴즈가 성공적으로 생성되었습니다.")
                .build();
    }


    @Transactional(readOnly = true)
    public List<QuizRandomResponse> getRandomNonAttemptedQuizzes(QuizCategory category, User user) {

        List<Long> correctQuizIds = quizUserRepository.findCorrectQuizIdsByUser(user);


        Pageable pageable = PageRequest.of(0, 10);
        List<Quiz> randomQuizzes = quizRepository.findQuizzesByCategoryExcludingIds(category, correctQuizIds, pageable);


        return randomQuizzes.stream()
                .map(QuizRandomResponse::of)
                .collect(Collectors.toList());

    }

    public QuizDetailResponse getQuiz(Long quizid){

        return quizRepository.findById(quizid)
                .map(quiz -> QuizDetailResponse.builder()
                        .id(quiz.getId())
                        .category(quiz.getCategory())
                        .question(quiz.getQuestion())
                        .example(quiz.getExample())
                        .answer(quiz.getAnswer())
                        .build())
                .orElseThrow(() -> new QuizCustomException(QuizExceptionCode.NOT_FOUND_QUIZ));
    }

    @Transactional
    public QuizUpdateResponse updateQuiz(Long quizid, QuizUpdateRequest updateRequest){
        Quiz quiz = quizRepository.findById(quizid)
                .orElseThrow(() -> new QuizCustomException(QuizExceptionCode.NOT_FOUND_QUIZ));

        quiz.updateQuiz(updateRequest.getQuestion(), updateRequest.getExample(), updateRequest.getCategory(), updateRequest.getAnswer());

        return QuizUpdateResponse.of(
                quiz.getId(),
                quiz.getCategory(),
                quiz.getQuestion(),
                quiz.getExample(),
                quiz.getAnswer()
        );
    }

    @Transactional
    public QuizDeleteResponse deleteQuiz(Long quizid){
        Quiz quiz = quizRepository.findById(quizid)
                .orElseThrow(() -> new QuizCustomException(QuizExceptionCode.NOT_FOUND_QUIZ));

        quizRepository.delete(quiz);
        return QuizDeleteResponse.of(quiz.getId(), "퀴즈가 성공적으로 삭제되었습니다");
    }

    @Transactional
    public QuizAnswerSubmitResponse submitQuizAnswer(Long quizId, String submittedAnswer, User user) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new QuizCustomException(QuizExceptionCode.NOT_FOUND_QUIZ));

        boolean isCorrect = quiz.getAnswer().equalsIgnoreCase(submittedAnswer);
        UserQuizStatus status;

        if ("pass".equalsIgnoreCase(submittedAnswer)) {
            status = UserQuizStatus.PASS;
        } else if (isCorrect) {
            status = UserQuizStatus.CORRECT;
            quiz.updateCount(quiz.getCorrectCount() + 1, quiz.getFailCount(), quiz.getSolveCount() + 1);
        } else {
            status = UserQuizStatus.FAIL;
            quiz.updateCount(quiz.getCorrectCount(), quiz.getFailCount() + 1, quiz.getSolveCount() + 1);
        }

        if (user != null) {
            UserQuiz userQuiz = UserQuiz.builder()
                    .user(user)
                    .quiz(quiz)
                    .status(status)
                    .score(Long.parseLong(status.getScore()))
                    .build();
            quizUserRepository.save(userQuiz);
        }

        String resultMessage = isCorrect ? "정답입니다!" : "틀렸습니다.";

        return QuizAnswerSubmitResponse.builder()
                .id(quizId)
                .submittedAnswer(submittedAnswer)
                .isCorrect(isCorrect)
                .correctAnswer(quiz.getAnswer())
                .resultMessage(resultMessage)
                .build();
    }

    @Transactional(readOnly = true)
    public List<QuizCorrectUserResponse> getCorrectQuizzesForUser(User user) {
        List<Quiz> correctQuizzes = quizUserRepository.findCorrectQuizzesByUser(user);

        return correctQuizzes.stream()
                .map(this::CorrectToDto)
                .toList();
    }

    private QuizCorrectUserResponse CorrectToDto(Quiz quiz) {

        return QuizCorrectUserResponse.builder()
                .id(quiz.getId())
                .category(quiz.getCategory())
                .question(quiz.getQuestion())
                .example(quiz.getExample())
                .answer(quiz.getAnswer())
                .build();
    }

    @Transactional(readOnly = true)
    public List<QuizFailUserResponse> getFailQuizzesForUser(User user) {
        List<Quiz> failQuizzes = quizUserRepository.findFAILQuizzesByUser(user);


        return failQuizzes.stream()
                .map(this::failToDto)
                .toList();
    }

    private QuizFailUserResponse failToDto(Quiz quiz) {

        return QuizFailUserResponse.builder()
                .id(quiz.getId())
                .category(quiz.getCategory())
                .question(quiz.getQuestion())
                .example(quiz.getExample())
                .build();
    }

    @Transactional(readOnly = true)
    public List<QuizPassUserResponse> getPassQuizzesForUser(User user) {
        List<Quiz> passQuizzes = quizUserRepository.findPASSQuizzesByUser(user);


        return passQuizzes.stream()
                .map(this::passToDto)
                .toList();
    }

    private QuizPassUserResponse passToDto(Quiz quiz) {

        return QuizPassUserResponse.builder()
                .id(quiz.getId())
                .category(quiz.getCategory())
                .question(quiz.getQuestion())
                .example(quiz.getExample())
                .build();
    }
}
