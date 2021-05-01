package io.github.siaust.web_quiz_app.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.Digits;

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

//    public Answer(Quiz quiz, int answer) {
//        this.quiz = quiz;
//        this.answer = answer;
//    }

    public Answer(int answer) {
        this.answer = answer;
    }

    /**
     * This constructor is required to handle form submission from the
     * <@code>/create</@code> endpoint, as input handled in HTML is by
     * default a String.
     * @param answer The integer value which represents an index for
     *               the correct answer in a list of Answers.
     */
    public Answer(String answer) {this.answer = Integer.parseInt(answer);}

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = Integer.parseInt(answer);
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
