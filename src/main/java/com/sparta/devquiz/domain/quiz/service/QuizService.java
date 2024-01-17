package com.sparta.devquiz.domain.quiz.service;

import com.sparta.devquiz.domain.coin.service.CoinService;
import com.sparta.devquiz.domain.quiz.dto.request.QuizCreateRequest;
import com.sparta.devquiz.domain.quiz.dto.request.QuizUpdateRequest;
import com.sparta.devquiz.domain.quiz.dto.response.QuizAnswerSubmitResponse;
import com.sparta.devquiz.domain.quiz.dto.response.QuizCreateResponse;
import com.sparta.devquiz.domain.quiz.dto.response.QuizDeleteResponse;
import com.sparta.devquiz.domain.quiz.dto.response.QuizDetailResponse;
import com.sparta.devquiz.domain.quiz.dto.response.QuizRandomResponse;
import com.sparta.devquiz.domain.quiz.dto.response.QuizUpdateResponse;
import com.sparta.devquiz.domain.quiz.entity.Quiz;
import com.sparta.devquiz.domain.quiz.entity.UserQuiz;
import com.sparta.devquiz.domain.quiz.entity.UserQuizId;
import com.sparta.devquiz.domain.quiz.enums.QuizCategory;
import com.sparta.devquiz.domain.quiz.enums.UserQuizStatus;
import com.sparta.devquiz.domain.quiz.exception.QuizCustomException;
import com.sparta.devquiz.domain.quiz.exception.QuizExceptionCode;
import com.sparta.devquiz.domain.quiz.repository.QuizRepository;
import com.sparta.devquiz.domain.quiz.repository.QuizUserRepository;
import com.sparta.devquiz.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuizService {

    private final QuizRepository quizRepository;
    private final QuizUserRepository quizUserRepository;
    private final CoinService coinService;

    @Transactional
    public QuizCreateResponse createQuiz(User user, QuizCreateRequest createRequest) {
        /* 일반유저가 접근하지 못한다면  유저검인증 필요없이 ROLE.admin 검증만 하는걸로 */
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
    public List<QuizRandomResponse> getRandomQuizList(QuizCategory category) {
        Pageable pageable = PageRequest.of(0, 10); // 첫 번째 페이지, 페이지 당 10개
        Page<Quiz> page = quizRepository.findQuizByCategory(category, pageable);
        List<Quiz> quizList = page.getContent();

        return QuizRandomResponse.of(quizList);

    }

    public QuizDetailResponse getQuiz(Long quizid){
        // 퀴즈 가져오는건 비회원도 가능하기 때문에 유저 검증 안함
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

        boolean isCorrect = quiz.getAnswer().equalsIgnoreCase(submittedAnswer); // 정답과 대소문자 구분 없이 비교
        UserQuizStatus status;

        if ("pass".equalsIgnoreCase(submittedAnswer)) {
            status = UserQuizStatus.PASS; // 사용자가 PASS를 선택
        } else if (isCorrect) {
            status = UserQuizStatus.CORRECT; // 정답
            quiz.updateCount(quiz.getCorrectCount() + 1, quiz.getFailCount(), quiz.getSolveCount() + 1);
        } else {
            status = UserQuizStatus.FAIL; // 오답
            quiz.updateCount(quiz.getCorrectCount(), quiz.getFailCount() + 1, quiz.getSolveCount() + 1);
        }

        // 유저가 로그인한 상태라면 UserQuiz 엔티티에 결과를 저장
        if (user != null) {
            // UserQuizId 객체를 만들어 사용자 ID와 퀴즈 ID를 설정합니다.
            UserQuizId userQuizId = new UserQuizId(user.getId(), quiz.getId());

            UserQuiz userQuiz = UserQuiz.builder()
                    .userQuizId(userQuizId) // UserQuizId 객체를 사용하여 UserQuiz 객체에 설정합니다.
                    .user(user)
                    .quiz(quiz)
                    .status(status)
                    .score(Long.parseLong(status.getScore())) // enum에서 정의된 점수 사용
                    .build();
            quizUserRepository.save(userQuiz);
        }

        String resultMessage = isCorrect ? "정답입니다!" : "틀렸습니다. 올바른 답은 " + quiz.getAnswer() + " 입니다.";

        return QuizAnswerSubmitResponse.builder()
                .id(quizId)
                .submittedAnswer(submittedAnswer)
                .isCorrect(isCorrect)
                .correctAnswer(quiz.getAnswer())
                .resultMessage(resultMessage)
                .build();
    }
}
