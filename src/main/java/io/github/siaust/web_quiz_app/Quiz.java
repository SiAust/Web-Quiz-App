package io.github.siaust.web_quiz_app;

import java.util.Arrays;

public class Quiz {

    private String title;
    private String text;
    private String[] options;


    public Quiz() {}

    public Quiz(String title, String text, String[] options) {
        this.title = title;
        this.text = text;
        this.options = options;
    }

    @Override
    public String toString() {
        return "title: " + title + " text: " + text + " options: " + Arrays.toString(options);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String[] getOptions() {
        return options;
    }

    public void setOptions(String[] options) {
        this.options = options;
    }
}
