package com.sparta.devquiz.domain.quiz.dto.quiz.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomQuizDto {

    private String title;

    private Long quizId;

    private List<Choice> choices;


    public static CustomQuizDto createForTest(String title, String choice1, String choice2,
                                              String choice3, String choice4, int answerIndex) {
        List<Choice> choices = new ArrayList<>();
        choices.add(Choice.builder().content(choice1).isAnswer(answerIndex == 1).build());
        choices.add(Choice.builder().content(choice2).isAnswer(answerIndex == 2).build());
        choices.add(Choice.builder().content(choice3).isAnswer(answerIndex == 3).build());
        choices.add(Choice.builder().content(choice4).isAnswer(answerIndex == 4).build());

        return CustomQuizDto.builder()
                .title(title)
                .choices(choices)
                .build();
    }

    public void checkCorrectAnswer() {
        long correctAnswerCount = this.choices.stream()
                .filter(CustomQuizDto.Choice::getIsAnswer)
                .count();


    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Getter
    public static class Choice {

        private Long choiceId;
        private String content;
        private boolean isAnswer;

        public boolean getIsAnswer() {
            return this.isAnswer;
        }
    }
}
