package com.sparta.devquiz.domain.quiz.service;

import com.sparta.devquiz.domain.coin.enums.CoinContent;
import com.sparta.devquiz.domain.coin.service.CoinService;
import com.sparta.devquiz.domain.quiz.dto.quiz.request.QuizAnswerSubmitRequest;
import com.sparta.devquiz.domain.quiz.dto.quiz.request.QuizCreateRequest;
import com.sparta.devquiz.domain.quiz.dto.quiz.request.QuizUpdateRequest;
import com.sparta.devquiz.domain.quiz.dto.quiz.response.QuizAnswerSubmitResponse;
import com.sparta.devquiz.domain.quiz.dto.quiz.response.QuizDetailInfoResponse;
import com.sparta.devquiz.domain.quiz.dto.quiz.response.QuizGetByUserResponse;
import com.sparta.devquiz.domain.quiz.dto.quiz.response.QuizRandomResponse;
import com.sparta.devquiz.domain.quiz.entity.Category;
import com.sparta.devquiz.domain.quiz.entity.Quiz;
import com.sparta.devquiz.domain.quiz.entity.QuizChoice;
import com.sparta.devquiz.domain.quiz.entity.UserQuiz;
import com.sparta.devquiz.domain.quiz.enums.QuizCategory;
import com.sparta.devquiz.domain.quiz.enums.UserQuizStatus;
import com.sparta.devquiz.domain.quiz.exception.CategoryCustomException;
import com.sparta.devquiz.domain.quiz.exception.CategoryExceptionCode;
import com.sparta.devquiz.domain.quiz.exception.QuizCustomException;
import com.sparta.devquiz.domain.quiz.exception.QuizExceptionCode;
import com.sparta.devquiz.domain.quiz.repository.CategoryRepository;
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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuizService {

    private final UserService userService;
    private final QuizRepository quizRepository;
    private final CategoryRepository categoryRepository;
    private final QuizUserRepository quizUserRepository;
    private final CoinService coinService;

    @Transactional
    public void createQuiz(QuizCreateRequest createRequest, User User, Long categoryId) {

        if (User == null) {
            throw new UserCustomException(UserExceptionCode.UNAUTHORIZED_USER);
        }
        if (User.getRole() != UserRole.ROLE_ADMIN) {
            throw new UserCustomException(UserExceptionCode.UNAUTHORIZED_USER);
        }

        // 카테고리 조회
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryCustomException(CategoryExceptionCode.NOT_FOUND_CATEGORY));
        // 퀴즈 생성
        Quiz quiz = Quiz.builder()
                .quizTitle(createRequest.getQuizTitle())
                .category(category)
                .build();

        // 선택지 추가하기
        List<QuizChoice> quizChoices = createRequest.getChoices().stream()
                .map(choiceDto -> QuizChoice.builder()
                        .quiz(quiz)
                        .choiceContent(choiceDto.getChoiceContent())
                        .isAnswer(choiceDto.isAnswer())
                        .build())
                .toList();

        quiz.addChoices(quizChoices);

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
        if (randomQuizzes.size()<10) {
            throw new QuizCustomException(QuizExceptionCode.NOT_FOUND_QUIZ);
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
    public void updateQuiz(Long quizId, QuizUpdateRequest updateRequest, User User, Long categoryId) {
        if (User == null) {
            throw new UserCustomException(UserExceptionCode.UNAUTHORIZED_USER);
        }
        if (User.getRole() != UserRole.ROLE_ADMIN) {
            throw new UserCustomException(UserExceptionCode.UNAUTHORIZED_USER);
        }
        Quiz quiz = getQuizById(quizId);
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryCustomException(CategoryExceptionCode.NOT_FOUND_CATEGORY));

        quiz.updateQuiz(updateRequest.getCategoryTitle(),
                updateRequest.getQuizTitle(),
                updateRequest.getChoices().stream()
                        .map(choiceDto -> QuizChoice.builder()
                                .quiz(quiz)
                                .choiceContent(choiceDto.getChoiceContent())
                                .isAnswer(choiceDto.isAnswer())
                                .build())
                                .toList());
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
