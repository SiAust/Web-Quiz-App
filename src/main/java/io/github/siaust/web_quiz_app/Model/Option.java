package io.github.siaust.web_quiz_app.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Component
@Entity(name = "options_table")
public class Option {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn
    @JsonBackReference
    private Quiz quiz;

    @NotEmpty(message = "Must enter an option")
    private String option;

    // for Jackson deserialization
    public Option() {
    }

    public Option(Quiz quiz, String option) {
        this.quiz = quiz;
        this.option = option;

    }

    public Option(String option) {
        this.option = option;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    @Override
    public String toString() {
        return option;
    }
}
