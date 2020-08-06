package io.github.siaust.web_quiz_app.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.siaust.web_quiz_app.Service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Component
@Entity(name = "quiz_table")
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // Won't send in JSON response
    private long userId;

    @NotBlank(message = "Title must not be blank")
    private String title;

    @NotBlank(message = "Text must not be blank")
    private String text;

    @Size(min = 2)
    @NotNull
    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Option> options;

    @JsonIgnore /* Ignores this JSON property in a generated response from this DTO */
    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL)
    @JsonManagedReference /* Prevents Jackson error looping JSON response */
    private List<Answer> answers;

    public Quiz() {
    }

    public Quiz(String title, String text, List<Option> options, List<Answer> answers) {
        this.title = title;
        this.text = text;
        this.options = options;
        this.answers = answers;
    }

    @Override
    public String toString() {
        return "id: " + id + " title: " + title + " text: " + text + " options: " + options
                + " answer: " + answers;
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

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }

    @JsonIgnore
    public List<Answer> getAnswers() {
        return answers;
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
}
