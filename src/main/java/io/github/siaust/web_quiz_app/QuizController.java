package io.github.siaust.web_quiz_app;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class QuizController {

    public QuizController() {}

    /* *** POST request mappings *** */

    /* Mapping for adding a quiz POST */
    @PostMapping(path = "api/quizzes", produces = "application/json")
    public Quiz addQuiz(@RequestBody Quiz quiz) {
        QuizModel.addQuiz(quiz);
        return quiz;
    }

    /* Mapping for user answer attempt POST */
    @RequestMapping(path = "/api/quizzes/{id}/solve", method = RequestMethod.POST)
    public Feedback answerQuiz(@RequestParam("answer") int answer,
                               @PathVariable int id) {

        Feedback feedback = new Feedback();
        feedback.setFeedBack(QuizModel.getQuiz(id).getAnswer() == answer);

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

    /* Spring will catch the exception and return a HTTP status */
    @ExceptionHandler(IndexOutOfBoundsException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleException() {
    }
}
