package io.github.siaust.web_quiz_app;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class QuizController {

    public QuizController() {}

    /* *** POST request mappings *** */

    /* Mapping for adding a quiz POST */
    @PostMapping(path = "api/quizzes", produces = "application/json")
    public Quiz addQuiz(@Valid @RequestBody Quiz quiz) {
        QuizModel.addQuiz(quiz);
        System.out.println(quiz);
        return quiz;
    }

    /* Mapping for user answer attempt POST */
    @RequestMapping(path = "/api/quizzes/{id}/solve", method = RequestMethod.POST)
    public Feedback answerQuiz(@RequestBody Answer answer,
                               @PathVariable int id) {
        System.out.println("POST answer array: " + Arrays.toString(answer.getAnswer()));
        Feedback feedback = new Feedback();
        boolean success = false;
        if (Arrays.equals(QuizModel.getQuiz(id).getAnswer(), answer.getAnswer())) {
            success = true;
        } else if (QuizModel.getQuiz(id).getAnswer() == null && answer.getAnswer().length == 0) {
            success = true;
        } else if (QuizModel.getQuiz(id).getAnswer() != null) {
            if (QuizModel.getQuiz(id).getAnswer().length == 0 && answer.getAnswer() == null) {
                success = true;
            }
        }
        feedback.setFeedBack(success);
        return feedback;
    }

    /* *** GET request mappings *** */

    /* Mapping for user request GET quiz with ID */
    @RequestMapping(value = "/api/quizzes/{id}", method = RequestMethod.GET)
    public Quiz getQuiz(@PathVariable int id) {
        return QuizModel.getQuiz(id);
    }

    /* Mapping for user request GET all quizzes */
    @RequestMapping(value = "/api/quizzes", method = RequestMethod.GET)
    public Quiz[] getAllQuizzes() {
        return QuizModel.getQuiz();
    }

    /* Replaced with ExceptionHandler class with Spring annotations */
   /* *//* Spring will catch the exception and return a HTTP status *//*
    @ExceptionHandler(IndexOutOfBoundsException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleException(Exception e) {

    }*/
}
