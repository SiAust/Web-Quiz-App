package io.github.siaust.web_quiz_app;

import java.util.ArrayList;
import java.util.List;

public class QuizModel {

    private static final List<Quiz> quizList = new ArrayList<>();

     /* For testing so I can make GET requests before POSTing a quiz */
    /*static {
        Quiz quiz = new Quiz("The Java Logo", "What is depicted on the Java logo?",
                new String[] {"Robot", "Tea leaf", "Cup of coffee", "Bug"}, 2);
        addQuiz(quiz);
    }*/

    public static Quiz[] getQuiz() {
        Quiz[] quizzes = new Quiz[quizList.size()];
        for (int i = 0; i < quizList.size(); i++) {
            quizzes[i] = quizList.get(i);
        }
        return quizzes;
    }

    public static Quiz getQuiz(int id) {
        return quizList.get(id -1);
    }

    public static void addQuiz(Quiz quiz) {
        quiz.setId(quizList.size() + 1);
        quizList.add(quiz);
    }
}
