package com.sparta.devquiz.domain.quiz.service;

import com.sparta.devquiz.domain.coin.enums.CoinContent;
import com.sparta.devquiz.domain.coin.service.CoinService;
import com.sparta.devquiz.domain.quiz.dto.quiz.request.QuizAnswerSubmitRequest;
import com.sparta.devquiz.domain.quiz.dto.quiz.request.QuizCreateRequest;
import com.sparta.devquiz.domain.quiz.dto.quiz.request.QuizUpdateRequest;
import com.sparta.devquiz.domain.quiz.dto.quiz.response.QuizDetailInfoResponse;
import com.sparta.devquiz.domain.quiz.dto.quiz.response.QuizGetByUserResponse;
import com.sparta.devquiz.domain.quiz.dto.quiz.response.QuizPassResponse;
import com.sparta.devquiz.domain.quiz.dto.quiz.response.QuizQueryResponse;
import com.sparta.devquiz.domain.quiz.dto.quiz.response.QuizRandomResponse;
import com.sparta.devquiz.domain.quiz.dto.quiz.response.QuizResultResponse;
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
import com.sparta.devquiz.domain.quiz.repository.QuizChoiceRepository;
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

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuizService {

    private final UserService userService;
    private final QuizRepository quizRepository;
    private final CategoryRepository categoryRepository;
    private final QuizUserRepository quizUserRepository;
    private final CoinService coinService;
    private final QuizChoiceRepository quizChoiceRepository;

    @Transactional
    public void createQuiz(QuizCreateRequest createRequest, User user, Long categoryId) {

        if (user == null) {
            throw new UserCustomException(UserExceptionCode.UNAUTHORIZED_USER);
        }
        if (user.getRole() != UserRole.ROLE_ADMIN) {
            throw new UserCustomException(UserExceptionCode.UNAUTHORIZED_USER);
        }


        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryCustomException(CategoryExceptionCode.NOT_FOUND_CATEGORY));

        Quiz quiz = quizRepository.save(Quiz.builder()
                .quizTitle(createRequest.getQuizTitle())
                .category(category)
                .build());

        List<QuizChoice> quizChoices = createRequest.getChoices().stream()
                .map(choiceDto -> QuizChoice.builder()
                        .choiceContent(choiceDto.getChoiceContent())
                        .isAnswer(choiceDto.isAnswer())
                        .quiz(quiz)
                        .build())
                .toList();
        for (QuizChoice choice : quizChoices) {
            quiz.addChoice(choice);
        }
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
        QuizChoice quizChoice = quizChoiceRepository.findQuizChoiceByQuiz(quiz)
                .orElseThrow(() -> new QuizCustomException(QuizExceptionCode.NOT_FOUND_QUIZ_CHOICE));

        return QuizDetailInfoResponse.of(quiz, quizChoice);
    }

    public List<QuizQueryResponse> getQuizzesByCategory(QuizCategory category, User user) {
        List<Quiz> CategoryQuizzes;
        Pageable pageable = PageRequest.of(1, 30);

        if (user == null) {
            throw new UserCustomException(UserExceptionCode.UNAUTHORIZED_USER);
        }
        if (user.getRole() != UserRole.ROLE_ADMIN) {
            throw new UserCustomException(UserExceptionCode.UNAUTHORIZED_USER);
        }

        CategoryQuizzes = quizRepository.findQuizzesByCategoryExcludingIds(category, pageable);

        return CategoryQuizzes.stream()
                .map(QuizQueryResponse::of)
                .toList();
    }
    @Transactional
    public void updateQuiz(QuizUpdateRequest updateRequest, User User, Long quizId, Long categoryId) {
        if (User == null) {
            throw new UserCustomException(UserExceptionCode.UNAUTHORIZED_USER);
        }
        if (User.getRole() != UserRole.ROLE_ADMIN) {
            throw new UserCustomException(UserExceptionCode.UNAUTHORIZED_USER);
        }
        Category category = categoryRepository.findById(updateRequest.getCategoryId())
                .orElseThrow(() -> new CategoryCustomException(CategoryExceptionCode.NOT_FOUND_CATEGORY));
        Quiz quiz = getQuizById(updateRequest.getQuizId());

        quiz.updateQuiz(updateRequest.getQuizTitle(),new ArrayList<>(), category);

        quizChoiceRepository.deleteAll(quiz.getQuizChoices());
        quiz.getQuizChoices().clear();


        List<QuizChoice> updatedChoices = updateRequest.getChoices().stream()
                .map(choiceDto -> QuizChoice.builder()
                        .choiceContent(choiceDto.getChoiceContent())
                        .isAnswer(choiceDto.isAnswer())
                        .build())
                .toList();

        updatedChoices.forEach(quiz::addChoice);
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
    public QuizResultResponse submitQuizAnswer(Long quizId, User user, QuizAnswerSubmitRequest request) {
        Quiz quiz = getQuizById(quizId);
        UserQuizStatus status;

        QuizChoice quizChoice = quizChoiceRepository.findById(request.getChoiceId())
                .orElseThrow(() -> new QuizCustomException(QuizExceptionCode.NOT_FOUND_QUIZ_CHOICE));

        QuizChoice submittedChoice = quiz.getQuizChoices().stream()
                .filter(choice -> quizChoice.getChoiceContent().equals(request.getChoiceId()))
                .findFirst()
                .orElseThrow(() -> new QuizCustomException(QuizExceptionCode.NOT_FOUND_QUIZ_CHOICE));

        boolean isCorrect = quizChoice.getIsAnswer();

        if (isCorrect) {
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

        return QuizResultResponse.of(quiz, submittedChoice.getChoiceContent(), status, isCorrect);
    }
    public QuizPassResponse passQuiz(Long quizId, User user, QuizAnswerSubmitRequest request){
        Quiz quiz = getQuizById(quizId);
        UserQuizStatus status = UserQuizStatus.PASS;
        QuizChoice quizChoice = quizChoiceRepository.findById(request.getChoiceId())
                .orElseThrow(() -> new QuizCustomException(QuizExceptionCode.NOT_FOUND_QUIZ_CHOICE));
        boolean isCorrect = quizChoice.getIsAnswer();


        if (user != null) {
            User findUser = userService.getUserById(user.getId());
            UserQuiz userQuiz = UserQuiz.builder()
                    .user(findUser)
                    .quiz(quiz)
                    .status(status)
                    .build();

            quizUserRepository.save(userQuiz);
        }

        return QuizPassResponse.of(quiz.getId());
    }

    public List<QuizSolvedGrassResponse> getSolvedGrassByUser(User user){
        return quizUserRepository.findSolvedGrassByUser(user);
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
