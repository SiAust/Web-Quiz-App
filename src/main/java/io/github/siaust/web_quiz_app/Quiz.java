package io.github.siaust.web_quiz_app;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.util.Arrays;

public class Quiz {

    private int id;
    @NotBlank(message = "Title must not be blank")
    private String title;
    @NotBlank(message = "Text must not be blank")
    private String text;
    @Size(min = 2)
    @NotNull
    private String[] options;
    @JsonIgnore
    private int[] answer;

    public Quiz() {}

    public Quiz(String title, String text, String[] options, int[] answer) {
        this.id = 0;
        this.title = title;
        this.text = text;
        this.options = options;
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "id: " + id + " title: " + title + " text: " + text + " options: " + Arrays.toString(options)
                + " answer: " + Arrays.toString(answer);
    }

    // Getters and setters for the Spring annotation @RequestBody to function
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

    @JsonIgnore
    public int[] getAnswer() {
        return answer;
    }

    @JsonProperty("answer")
    public void setAnswer(int[] answer) {
        this.answer = answer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
