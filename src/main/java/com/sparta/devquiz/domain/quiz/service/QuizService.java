package com.sparta.devquiz.domain.quiz.service;

import com.sparta.devquiz.domain.category.entity.Category;
import com.sparta.devquiz.domain.category.enums.QuizCategory;
import com.sparta.devquiz.domain.category.repository.CategoryRepository;
import com.sparta.devquiz.domain.coin.enums.CoinContent;
import com.sparta.devquiz.domain.coin.service.CoinService;
import com.sparta.devquiz.domain.quiz.dto.request.QuizAnswerSubmitRequest;
import com.sparta.devquiz.domain.quiz.dto.request.QuizCreateRequest;
import com.sparta.devquiz.domain.quiz.dto.request.QuizUpdateRequest;
import com.sparta.devquiz.domain.quiz.dto.response.*;
import com.sparta.devquiz.domain.quiz.entity.Quiz;
import com.sparta.devquiz.domain.quiz.entity.QuizChoice;
import com.sparta.devquiz.domain.quiz.entity.UserQuiz;
import com.sparta.devquiz.domain.quiz.enums.UserQuizStatus;
import com.sparta.devquiz.domain.quiz.exception.QuizChoiceCustomException;
import com.sparta.devquiz.domain.quiz.exception.QuizChoiceExceptionCode;
import com.sparta.devquiz.domain.quiz.exception.QuizCustomException;
import com.sparta.devquiz.domain.quiz.exception.QuizExceptionCode;
import com.sparta.devquiz.domain.quiz.repository.QuizChoiceRepository;
import com.sparta.devquiz.domain.quiz.repository.QuizRepository;
import com.sparta.devquiz.domain.quiz.repository.QuizUserRepository;
import com.sparta.devquiz.domain.user.entity.User;
import com.sparta.devquiz.domain.user.enums.UserRole;
import com.sparta.devquiz.domain.user.exception.UserCustomException;
import com.sparta.devquiz.domain.user.exception.UserExceptionCode;
import com.sparta.devquiz.domain.user.repository.UserRepository;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class QuizService {

    private final UserRepository userRepository;
    private final QuizRepository quizRepository;
    private final CategoryRepository categoryRepository;
    private final QuizUserRepository quizUserRepository;
    private final CoinService coinService;
    private final QuizChoiceRepository quizChoiceRepository;


    @Transactional(readOnly = true)
    public List<QuizRandomResponse> getRandomNonAttemptedQuizzes(QuizCategory quizCategory, User user) {
        List<Quiz> randomQuizzes;
        Pageable pageable = PageRequest.of(0, 10);
        Category category = categoryRepository.findByCategoryTitleOrElseThrow(quizCategory.get());

        if (user == null) {
            randomQuizzes = quizRepository.findQuizByCategoryAndIsDeletedFalse(category, pageable);
        } else {
            List<Long> correctQuizIds = quizUserRepository.findCorrectQuizIdsByUser(user);

            if (correctQuizIds.isEmpty()) {
                randomQuizzes = quizRepository.findQuizzesByCategoryExcludingIds(category, pageable);
            } else {
                randomQuizzes = quizRepository.findQuizzesByCategoryExcludingIds(category, correctQuizIds, pageable);
            }
        }
        if (randomQuizzes.size() < 10) {
            throw new QuizCustomException(QuizExceptionCode.NOT_ENOUGH_QUIZ);
        }
        return randomQuizzes.stream()
                .map(QuizRandomResponse::of)
                .toList();
    }

    @Transactional(readOnly = true)
    public QuizDetailInfoResponse getQuiz(Long quizId) {

        Quiz quiz = quizRepository.findQuizByIdOrElseThrow(quizId);
        Category category = quiz.getCategory();
        List<QuizChoice> quizChoices = quizChoiceRepository.findQuizChoicesByQuiz(quiz);

        return QuizDetailInfoResponse.builder()
                .id(quiz.getId())
                .categoryTitle(category.getCategoryTitle())
                .quizTitle(quiz.getQuizTitle())
                .quizChoices(quizChoices)
                .build();
    }

    public QuizResultResponse submitQuizAnswer(Long quizId, User user, QuizAnswerSubmitRequest request) {
        Quiz quiz = quizRepository.findQuizByIdOrElseThrow(quizId);
        UserQuizStatus status, coinStatus;

        int choiceSequence = request.getChoiceSequence();
        QuizChoice quizChoice = quizChoiceRepository.findByQuizChoiceByChoiceSequenceOrElseThrow(quizId, choiceSequence);

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
            coinStatus = status;
            User findUser = userRepository.findByIdOrElseThrow(user.getId());

            int score = status.getScore();

            if (isCorrect && quizUserRepository.isFirst(user)) {
                coinStatus = UserQuizStatus.FIRST;
                score = UserQuizStatus.FIRST.getScore();
            }

            UserQuiz userQuiz = UserQuiz.builder()
                .user(findUser)
                .quiz(quiz)
                .status(status)
                .score(score)
                .build();

            CoinContent coinContent = CoinContent.matchingQuizStatus(coinStatus);
            coinService.saveCoin(findUser.getId(), coinContent, findUser);
            findUser.updateWeekScore(score);

            quizUserRepository.save(userQuiz);
        }
        return QuizResultResponse.of(quiz, choiceSequence, quizChoice.getChoiceContent(), status, isCorrect);
    }

    public QuizPassResponse passQuiz(Long quizId, User user){
        Quiz quiz = quizRepository.findQuizByIdOrElseThrow(quizId);
        UserQuizStatus status = UserQuizStatus.PASS;

        if (user != null) {
            User findUser = userRepository.findByIdOrElseThrow(user.getId());
            UserQuiz userQuiz = UserQuiz.builder()
                    .user(findUser)
                    .quiz(quiz)
                    .status(status)
                    .build();

            quizUserRepository.save(userQuiz);
        }

        return QuizPassResponse.of(quiz.getId());
    }

    @Transactional(readOnly = true)
    public List<QuizQueryResponse> getQuizzesByCategory(QuizCategory quizCategory, User user) {
        if (user == null) {
            throw new UserCustomException(UserExceptionCode.UNAUTHORIZED_USER);
        }
        if (user.getRole() != UserRole.ROLE_ADMIN) {
            throw new UserCustomException(UserExceptionCode.UNAUTHORIZED_USER);
        }

        Pageable pageable = PageRequest.of(0, 30);
        Category category = categoryRepository.findByCategoryTitleOrElseThrow(quizCategory.get());
        List<Quiz> quizzes = quizRepository.findQuizByCategoryAndIsDeletedFalse(category, pageable);

        return quizzes.stream()
                .map(QuizQueryResponse::of)
                .toList();
    }

    public void createQuiz(QuizCreateRequest createRequest, User user, QuizCategory quizCategory) {

        if (user == null) {
            throw new UserCustomException(UserExceptionCode.UNAUTHORIZED_USER);
        }
        if (user.getRole() != UserRole.ROLE_ADMIN) {
            throw new UserCustomException(UserExceptionCode.UNAUTHORIZED_USER);
        }

        Category category = categoryRepository.findByCategoryTitleOrElseThrow(quizCategory.get());

        Quiz quiz = quizRepository.save(Quiz.builder()
                .quizTitle(createRequest.getTitle())
                .category(category)
                .correctCount(0L)
                .failCount(0L)
                .solveCount(0L)
                .isDeleted(false)
                .build());

        IntStream.range(0, createRequest.getChoices().size())
                .mapToObj(index -> {
                    QuizCreateRequest.Choice choiceDto = createRequest.getChoices().get(index);
                    if (choiceDto.getContent() == null || choiceDto.getContent().isEmpty()) {
                        throw new QuizChoiceCustomException(QuizChoiceExceptionCode.BAD_REQUEST_QUIZ_CHOICE);
                    }
                    QuizChoice choice = QuizChoice.builder()
                            .choiceContent(choiceDto.getContent())
                            .choiceSequence(index+1)
                            .isAnswer(choiceDto.getIsAnswer())
                            .build();
                    quiz.addChoice(choice);
                    return choice;
                })
                .collect(Collectors.toList());
    }

    public void updateQuiz(QuizUpdateRequest updateRequest, User User, Long quizId) {
        if (User == null) {
            throw new UserCustomException(UserExceptionCode.UNAUTHORIZED_USER);
        }
        if (User.getRole() != UserRole.ROLE_ADMIN) {
            throw new UserCustomException(UserExceptionCode.UNAUTHORIZED_USER);
        }
        Quiz quiz = quizRepository.findQuizByIdOrElseThrow(quizId);

        Category category = categoryRepository.findByIdOrElseThrow(updateRequest.getCategoryId());

        quiz.updateQuizTitle(updateRequest.getTitle());

        if (!quiz.getCategory().equals(category)) {
            quiz.addCategory(category);
        }

        quizChoiceRepository.deleteAllInBatch(quiz.getQuizChoices());

        List<QuizChoice> updatedChoices = IntStream.range(0, updateRequest.getChoices().size())
                .mapToObj(index -> {
                    QuizUpdateRequest.ChoiceUpdate choiceDto = updateRequest.getChoices().get(index);
                    return QuizChoice.builder()
                            .choiceContent(choiceDto.getContent())
                            .choiceSequence(index + 1)
                            .isAnswer(choiceDto.updateIsAnswer())
                            .quiz(quiz)
                            .build();
                })
                .collect(Collectors.toList());

        quizChoiceRepository.saveAll(updatedChoices);

        quizRepository.save(quiz);
    }

    public void deleteQuiz(Long quizId, User User) {
        if (User == null) {
            throw new UserCustomException(UserExceptionCode.UNAUTHORIZED_USER);
        }
        if (User.getRole() != UserRole.ROLE_ADMIN) {
            throw new UserCustomException(UserExceptionCode.UNAUTHORIZED_USER);
        }
        Quiz quiz = quizRepository.findQuizByIdOrElseThrow(quizId);

        quiz.deleteQuiz();
    }

}
