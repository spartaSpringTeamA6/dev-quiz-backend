package com.sparta.devquiz.domain.quiz.service;

import com.sparta.devquiz.domain.coin.enums.CoinContent;
import com.sparta.devquiz.domain.coin.service.CoinService;
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
import com.sparta.devquiz.domain.user.enums.UserRole;
import com.sparta.devquiz.domain.user.exception.UserCustomException;
import com.sparta.devquiz.domain.user.exception.UserExceptionCode;
import com.sparta.devquiz.domain.user.service.command.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuizService {

    private final UserService userService;
    private final QuizRepository quizRepository;
    private final QuizUserRepository quizUserRepository;
    private final CoinService coinService;

    @Transactional
    public void createQuiz(QuizCreateRequest createRequest, User User) {

        if (User == null) {
            throw new UserCustomException(UserExceptionCode.UNAUTHORIZED_USER);
        }
        if (User.getRole() != UserRole.ROLE_ADMIN) {
            throw new UserCustomException(UserExceptionCode.UNAUTHORIZED_USER);
        }
        String Example = String.join("\n", createRequest.getExample());
        Quiz quiz = Quiz.builder()
                .category(createRequest.getCategory())
                .question(createRequest.getQuestion())
                .example(Example)
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
            if (correctQuizIds.isEmpty()) {
                randomQuizzes = quizRepository.findQuizzesByCategoryExcludingIds(category, pageable);
            } else {
                randomQuizzes = quizRepository.findQuizzesByCategoryExcludingIds(category, correctQuizIds, pageable);
            }
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
    public void updateQuiz(Long quizId, QuizUpdateRequest updateRequest, User User) {
        if (User == null) {
            throw new UserCustomException(UserExceptionCode.UNAUTHORIZED_USER);
        }
        if (User.getRole() != UserRole.ROLE_ADMIN) {
            throw new UserCustomException(UserExceptionCode.UNAUTHORIZED_USER);
        }
        Quiz quiz = getQuizById(quizId);

        quiz.updateQuiz(updateRequest.getQuestion(), String.join("\n", updateRequest.getExample()),
                updateRequest.getCategory(), updateRequest.getAnswer());
    }

    @Transactional
    public void deleteQuiz(Long quizId, User User) {
        if (User == null) {
            throw new UserCustomException(UserExceptionCode.UNAUTHORIZED_USER);
        }
        if (User.getRole() != UserRole.ROLE_ADMIN) {
            throw new UserCustomException(UserExceptionCode.UNAUTHORIZED_USER);
        }
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
            User findUser = userService.getUserById(user.getId());
            int score = status.getScore();
            UserQuiz userQuiz = UserQuiz.builder()
                    .user(findUser)
                    .quiz(quiz)
                    .status(status)
                    .score(score)
                    .build();

            CoinContent coinContent = CoinContent.matchingQuizStatus(status);
            coinService.saveCoin(findUser.getId(), coinContent, findUser);
            findUser.updateWeekScore(score);

            quizUserRepository.save(userQuiz);
        }

        return QuizAnswerSubmitResponse.of(quiz, request.getAnswer(), status);
    }

    public List<QuizGetByUserResponse> getAllQuizzesForUser(User user) {
        return quizUserRepository.findCorrectQuizzesByUsers(user);
    }

    public List<QuizGetByUserResponse> getCorrectQuizzesForUser(User user) {
        return quizUserRepository.findCorrectQuizzesByUsers(user, UserQuizStatus.CORRECT);
    }

    public List<QuizGetByUserResponse> getFailQuizzesForUser(User user) {
        return quizUserRepository.findCorrectQuizzesByUsers(user, UserQuizStatus.FAIL);
    }

    public List<QuizGetByUserResponse> getPassQuizzesForUser(User user) {
        return quizUserRepository.findCorrectQuizzesByUsers(user, UserQuizStatus.PASS);
    }

    public Quiz getQuizById(Long id) {
        return quizRepository.findById(id)
                .orElseThrow(() -> new QuizCustomException(QuizExceptionCode.NOT_FOUND_QUIZ));
    }
}
