package io.github.siaust.web_quiz_app.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Component
@Entity(name = "quiz_table")
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private LocalDateTime timestamp;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // Won't send in JSON response
    private long userId;

    @NotBlank(message = "Title must not be blank")
    private String title;

    @NotBlank(message = "Question text must not be blank")
    private String text;

    private boolean isMultipleChoice;

    @NotNull
    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL)
    @JsonManagedReference
    @Valid
    @Size(min = 2, max = 6, message = "Must be at least two, no more than six options")
    private List<Option> options /*= new ArrayList<>()*/; // for Thymeleaf form

    @JsonIgnore /* Ignores this JSON property in a generated response from this DTO */
    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL)
    @JsonManagedReference /* Prevents Jackson error looping JSON response */
    @Valid
    @Size(min = 1, max = 4, message = "Must be at least one, no more than 4 answer(s)!")
    private List<Answer> answers/* = new ArrayList<>()*/; // for Thymeleaf form

    public Quiz() {
    }

    public Quiz(String title, String text, boolean isMultipleChoice
            , List<Option> options, List<Answer> answers) {
        this.title = title;
        this.text = text;
        this.isMultipleChoice = isMultipleChoice;
        this.options = options;
        this.answers = answers;
        this.timestamp = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "id: " + id
                + " title: " + title
                + " text: " + text
                + " isMultipleChoice: " + isMultipleChoice
                + " options: " + options
                + " answer: " + answers;
    }

    @AssertTrue
    public boolean isListValidSize() {
        return this.options.size() <= 6;
    }

    /* Getters and setters for the Spring annotation @RequestBody to function */
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

    public boolean isIsMultipleChoice() {
        return isMultipleChoice;
    }

    public void setIsMultipleChoice(boolean isMultipleChoice) {
        this.isMultipleChoice = isMultipleChoice;
    }

    public List<Option> getOptions() {
        return this.options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }

    @JsonIgnore
    public List<Answer> getAnswers() {
        return this.answers;
    }

    @JsonProperty("answer")
    public void setAnswers(List<Answer> answer) {
        this.answers = answer;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public LocalDateTime getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(LocalDateTime creationDate) {
        this.timestamp = creationDate;
    }
}
