package io.github.siaust.web_quiz_app;

import java.util.ArrayList;
import java.util.List;

public class QuizModel {

    private static final List<Quiz> quizList = new ArrayList<>();

    private static int answer;

    static {
        addQuiz(new Quiz("The Java Logo", "What is depicted on the Java logo?",
                new String[] {"Robot", "Tea leaf", "Cup of coffee", "Bug"}));
    }

    public static Quiz getQuiz(int id) {
        return quizList.get(id);
    }

    public static void addQuiz(Quiz quiz) {
        quizList.add(quiz);
    }

    public static int getAnswer() {
        return answer;
    }

    public static void setAnswer(int answer) {
        QuizModel.answer = answer;
    }
}
