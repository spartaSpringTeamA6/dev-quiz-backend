package com.sparta.devquiz.domain.quiz.service;

import com.sparta.devquiz.domain.quiz.dto.request.QuizCreateRequest;
import com.sparta.devquiz.domain.quiz.dto.request.QuizRandomRequest;
import com.sparta.devquiz.domain.quiz.dto.request.QuizUpdateRequest;
import com.sparta.devquiz.domain.quiz.dto.response.QuizAnswerSubmitResponse;
import com.sparta.devquiz.domain.quiz.dto.response.QuizCreateResponse;
import com.sparta.devquiz.domain.quiz.dto.response.QuizDeleteResponse;
import com.sparta.devquiz.domain.quiz.dto.response.QuizDetailResponse;
import com.sparta.devquiz.domain.quiz.dto.response.QuizRandomResponse;
import com.sparta.devquiz.domain.quiz.dto.response.QuizUpdateResponse;
import com.sparta.devquiz.domain.quiz.entity.Quiz;
import com.sparta.devquiz.domain.quiz.exception.QuizCustomException;
import com.sparta.devquiz.domain.quiz.exception.QuizExceptionCode;
import com.sparta.devquiz.domain.quiz.repository.QuizRepository;
import com.sparta.devquiz.domain.quiz.repository.QuizUserRepository;
import com.sparta.devquiz.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuizService {

    private final QuizRepository quizRepository;
//    private final UserRepository userRepository;
//    private final UserService userService;
    private final QuizUserRepository quizUserRepository;

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
    public List<QuizRandomResponse.QuizDto> getRandomQuizList(QuizRandomRequest quizRandomRequest){
        List<Quiz> quizList = quizRepository.findRandomQuizlistByCategory(quizRandomRequest.getCategory());
        return quizList.stream()
                .map(quiz -> new QuizRandomResponse.QuizDto(quiz.getId(), quiz.getQuestion(), quiz.getExample()))
                .toList();

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
    public QuizAnswerSubmitResponse submitQuizAnswer(Long quizId, String submittedAnswer) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new QuizCustomException(QuizExceptionCode.NOT_FOUND_QUIZ));

        boolean isCorrect = quiz.getAnswer().equals(submittedAnswer);
        if (isCorrect) {
            quiz.getCorrectCount();
        } else {
            quiz.getFailCount();
        } quiz.getSolveCount();

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
