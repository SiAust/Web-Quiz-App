package io.github.siaust.web_quiz_app;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class QuizController {

    List<Quiz> quizzes = new ArrayList<>();

    public QuizController() {}

    /* Mapping for adding a quiz POST */
    @PostMapping(path = "/quiz")
    public void addQuiz(@RequestBody Quiz quiz) {
        quizzes.add(quiz);
        System.out.println(quiz);
    }

    /* Mapping for user answer POST */
    @RequestMapping(path = "/api/quiz", method = RequestMethod.POST)
    public ResponseEntity<String> answerQuiz(@RequestParam("answer") int answer) {
        QuizModel.setAnswer(answer);
        System.out.println("answer: " + answer);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Content-Type", "application/json");

        String body;

        if (QuizModel.getAnswer() == 2) {
            body = "{\"success\":true,\"feedback\":\"Congratulations, you're right!\"}";
        } else {
            body = "{\"success\":false,\"feedback\":\"Wrong answer! Please, try again.\"}";
        }
        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(body);
    }

    /* Mapping for user request GET quiz */
    @RequestMapping(value = "/api/quiz", method = RequestMethod.GET)
//    @ResponseBody
    public Quiz getQuiz() {
        return QuizModel.getQuiz(0);
    }

}
