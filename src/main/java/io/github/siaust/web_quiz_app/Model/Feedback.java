package io.github.siaust.web_quiz_app.Model;

public class Feedback {

    private boolean success;
    private String feedback;


    public Feedback() {}

    public void setFeedBack(boolean answer) {
        if (answer) {
            success = true;
            feedback = "Congratulations, you're right!";
            return;
        }
        success = false;
        feedback = "Wrong answer! Please, try again.";
    }

    @Override
    public String toString() {
        return "success: " + success + " feedback: " + feedback;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}