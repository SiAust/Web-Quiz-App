package io.github.siaust.web_quiz_app.Model;

import java.util.Arrays;

public class Feedback {

    private boolean success;
    private int[] answers;

    public Feedback(boolean success, int[] answers) {
        this.success = success;
        this.answers = answers;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int[] getAnswers() {
        return answers;
    }

    public void setAnswers(int[] answers) {
        this.answers = answers;
    }

    @Override
    public String toString() {
        return "success: " + success + " answers: " + Arrays.toString(answers);
    }
}