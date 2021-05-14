package io.github.siaust.web_quiz_app.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.List;

@Component
@Entity(name = "quiz_table")
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private LocalDateTime timestamp;

//    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // Won't send in JSON response
    @ManyToOne()
    private User createdBy;

    private String topic;

    @NotBlank(message = "Question text must not be blank")
    private String text;

    private boolean isMultipleChoice;

    @JsonIgnore
    @NotNull
    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL)
    @JsonManagedReference
    @Valid
    @Size(min = 2, max = 6, message = "Must be at least two, no more than six options")
    private List<Option> options;

    @JsonIgnore /* Ignores this JSON property in a generated response from this DTO */
    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL)
    @JsonManagedReference /* Prevents Jackson error looping JSON response */
    @Valid
    private List<Answer> answers;

    public Quiz() {
    }

    public Quiz(String topic, String text, boolean isMultipleChoice
            , List<Option> options, List<Answer> answers) {
        this.topic = topic;
        this.text = text;
        this.isMultipleChoice = isMultipleChoice;
        this.options = options;
        this.answers = answers;
        this.timestamp = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "id: " + id
                + " topic: " + topic
                + " text: " + text
                + " timeStamp: " + timestamp
                + " isMultipleChoice: " + isMultipleChoice
                + " options: " + options
                + " answer: " + answers;
    }

//    @AssertTrue
//    public boolean isListValidSize() {
//        return this.options.size() <= 6;
//    }

    /* Getters and setters for the Spring annotation @RequestBody to function */

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
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

//    public long getUserId() {
//        return userId;
//    }
//
//    public void setUserId(long userId) {
//        this.userId = userId;
//    }


    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User user) {
        this.createdBy = user;
    }

    public LocalDateTime getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(LocalDateTime creationDate) {
        this.timestamp = creationDate;
    }
}
