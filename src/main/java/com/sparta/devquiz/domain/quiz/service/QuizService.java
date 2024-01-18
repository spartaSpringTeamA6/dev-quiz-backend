package com.sparta.devquiz.domain.quiz.service;

import com.sparta.devquiz.domain.quiz.dto.request.QuizCreateRequest;
import com.sparta.devquiz.domain.quiz.dto.request.QuizUpdateRequest;
import com.sparta.devquiz.domain.quiz.dto.response.QuizAnswerSubmitResponse;
import com.sparta.devquiz.domain.quiz.dto.response.QuizCorrectUserResponse;
import com.sparta.devquiz.domain.quiz.dto.response.QuizDetailResponse;
import com.sparta.devquiz.domain.quiz.dto.response.QuizFailUserResponse;
import com.sparta.devquiz.domain.quiz.dto.response.QuizPassUserResponse;
import com.sparta.devquiz.domain.quiz.dto.response.QuizRandomResponse;
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
    public void createQuiz(User user, QuizCreateRequest createRequest) {

        Quiz quiz = Quiz.builder()
                .category(createRequest.getCategory())
                .question(createRequest.getQuestion())
                .example(createRequest.getExample())
                .answer(createRequest.getAnswer())
                .correctCount(0L)
                .failCount(0L)
                .solveCount(0L)
                .isDeleted(false)
                .build();

        Quiz savedQuiz = quizRepository.save(quiz);
    }

    public List<QuizRandomResponse> getRandomNonAttemptedQuizzes(QuizCategory category, User user) {

        List<Long> correctQuizIds = quizUserRepository.findCorrectQuizIdsByUser(user);

        Pageable pageable = PageRequest.of(0, 10);
        List<Quiz> randomQuizzes = quizRepository.findQuizzesByCategoryExcludingIds(category,
                correctQuizIds, pageable);

        return randomQuizzes.stream()
                .map(QuizRandomResponse::of)
                .collect(Collectors.toList());
    }

    public QuizDetailResponse getQuiz(Long quizId) {

        return quizRepository.findById(quizId)
                .map(QuizDetailResponse::of)
                .orElseThrow(() -> new QuizCustomException(QuizExceptionCode.NOT_FOUND_QUIZ));
    }

    @Transactional
    public void updateQuiz(Long quizId, QuizUpdateRequest updateRequest) {
        Quiz quiz = getQuizById(quizId);

        quiz.updateQuiz(updateRequest.getQuestion(), updateRequest.getExample(),
                updateRequest.getCategory(), updateRequest.getAnswer());
    }

    @Transactional
    public void deleteQuiz(Long quizId) {
        Quiz quiz = getQuizById(quizId);

        quiz.deleteQuiz();
    }

    @Transactional
    public QuizAnswerSubmitResponse submitQuizAnswer(Long quizId, String submittedAnswer,
            User user) {
        Quiz quiz = getQuizById(quizId);

        boolean isCorrect = quiz.getAnswer().equalsIgnoreCase(submittedAnswer);
        UserQuizStatus status;

        if ("pass".equalsIgnoreCase(submittedAnswer)) {
            status = UserQuizStatus.PASS;
        } else if (isCorrect) {
            status = UserQuizStatus.CORRECT;
            quiz.updateCount(quiz.getCorrectCount() + 1, quiz.getFailCount(),
                    quiz.getSolveCount() + 1);
        } else {
            status = UserQuizStatus.FAIL;
            quiz.updateCount(quiz.getCorrectCount(), quiz.getFailCount() + 1,
                    quiz.getSolveCount() + 1);
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

        return QuizAnswerSubmitResponse.of(quiz, submittedAnswer, status);
    }

    public List<QuizCorrectUserResponse> getCorrectQuizzesForUser(User user) {
        List<Quiz> correctQuizzes = quizUserRepository.findCorrectQuizzesByUser(user);

        return correctQuizzes.stream()
                .map(QuizCorrectUserResponse::of)
                .toList();
    }

    public List<QuizFailUserResponse> getFailQuizzesForUser(User user) {
        List<Quiz> failQuizzes = quizUserRepository.findFAILQuizzesByUser(user);

        return failQuizzes.stream()
                .map(QuizFailUserResponse::of)
                .toList();
    }

    public List<QuizPassUserResponse> getPassQuizzesForUser(User user) {
        List<Quiz> passQuizzes = quizUserRepository.findPASSQuizzesByUser(user);

        return passQuizzes.stream()
                .map(QuizPassUserResponse::of)
                .toList();
    }

    public Quiz getQuizById(Long id) {
        return quizRepository.findById(id)
                .orElseThrow(() -> new QuizCustomException(QuizExceptionCode.NOT_FOUND_QUIZ));
    }
}
