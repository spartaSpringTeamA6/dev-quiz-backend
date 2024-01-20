package com.sparta.devquiz.domain.quiz.service;

import com.sparta.devquiz.domain.quiz.dto.request.QuizAnswerSubmitRequest;
import com.sparta.devquiz.domain.quiz.dto.request.QuizCreateRequest;
import com.sparta.devquiz.domain.quiz.dto.request.QuizUpdateRequest;
import com.sparta.devquiz.domain.quiz.dto.response.QuizAnswerSubmitResponse;
import com.sparta.devquiz.domain.quiz.dto.response.QuizDetailInfoResponse;
import com.sparta.devquiz.domain.quiz.dto.response.QuizGetByUserResponse;
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
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuizService {

    private final QuizRepository quizRepository;
    private final QuizUserRepository quizUserRepository;

    @Transactional
    public void createQuiz(QuizCreateRequest createRequest) {

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

        quizRepository.save(quiz);
    }

    public List<QuizRandomResponse> getRandomNonAttemptedQuizzes(QuizCategory category, User user) {

        List<Quiz> randomQuizzes;
        Pageable pageable = PageRequest.of(0, 10);

        if (user == null) {
            randomQuizzes = quizRepository.findQuizByCategory(category, pageable);
        } else {
            List<Long> correctQuizIds = quizUserRepository.findCorrectQuizIdsByUser(user);
            randomQuizzes = quizRepository.findQuizzesByCategoryExcludingIds(category, correctQuizIds, pageable);
        }

        return randomQuizzes.stream()
                .map(QuizRandomResponse::of)
                .toList();
    }

    public QuizDetailInfoResponse getQuiz(Long quizId) {
        Quiz quiz = getQuizById(quizId);

        return QuizDetailInfoResponse.of(quiz);
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
    public QuizAnswerSubmitResponse submitQuizAnswer(Long quizId, User user, QuizAnswerSubmitRequest request) {
        Quiz quiz = getQuizById(quizId);

        boolean isCorrect = quiz.getAnswer().equalsIgnoreCase(request.getAnswer());
        UserQuizStatus status;

        if ("0".equalsIgnoreCase(request.getAnswer())) {
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

        return QuizAnswerSubmitResponse.of(quiz, request.getAnswer(), status);
    }

    public List<QuizGetByUserResponse> getAllQuizzesForUser(User user) {
        return quizUserRepository.findCorrectQuizzesByUsers(user);
    }

    public List<QuizGetByUserResponse> getCorrectQuizzesForUser(User user) {
        return quizUserRepository.findCorrectQuizzesByUsers(user,UserQuizStatus.CORRECT);
    }

    public List<QuizGetByUserResponse> getFailQuizzesForUser(User user) {
        return quizUserRepository.findCorrectQuizzesByUsers(user,UserQuizStatus.FAIL);
    }

    public List<QuizGetByUserResponse> getPassQuizzesForUser(User user) {
        return quizUserRepository.findCorrectQuizzesByUsers(user,UserQuizStatus.PASS);
    }

    public Quiz getQuizById(Long id) {
        return quizRepository.findById(id)
                .orElseThrow(() -> new QuizCustomException(QuizExceptionCode.NOT_FOUND_QUIZ));
    }
}
