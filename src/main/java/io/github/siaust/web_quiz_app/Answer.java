package io.github.siaust.web_quiz_app;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Component
@Entity
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn
    @JsonBackReference
    private Quiz quiz;

    private int answer;

    public Answer() {}

    public Answer(Quiz quiz, int answer) {
        this.quiz = quiz;
        this.answer = answer;
    }

    public Answer(int answer) {
        this.answer = answer;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    @Override
    public String toString() {
        return String.valueOf(answer);
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
