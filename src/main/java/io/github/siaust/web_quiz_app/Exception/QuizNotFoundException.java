package io.github.siaust.web_quiz_app.Exception;

public class QuizNotFoundException extends RuntimeException {

    public QuizNotFoundException(int id) {
        super(String.format("Quiz with id: %d not found", id));
    }

    public QuizNotFoundException(String message) {
        super(message);
    }
}
