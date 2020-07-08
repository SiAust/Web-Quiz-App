package io.github.siaust.web_quiz_app;

public class Answer {

    private boolean success;
    private String feedback;


    public Answer() {}

    public Answer(boolean success, String feedback) {
        this.success = success;
        this.feedback = feedback;
    }

    @Override
    public String toString() {
        return "success: " + success + " feedback: " + feedback;
    }
}
