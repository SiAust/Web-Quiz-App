package io.github.siaust.web_quiz_app.Controller;

import io.github.siaust.web_quiz_app.Model.Answer;
import io.github.siaust.web_quiz_app.Model.Feedback;
import io.github.siaust.web_quiz_app.Model.Quiz;
import io.github.siaust.web_quiz_app.Exception.QuizNotFoundException;
import io.github.siaust.web_quiz_app.Repository.QuizRepository;
import io.github.siaust.web_quiz_app.Repository.UserRepository;
import io.github.siaust.web_quiz_app.Service.QuizService;
import io.github.siaust.web_quiz_app.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.http.HttpResponse;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class QuizController {

    private QuizRepository quizRepository;

    public QuizController() {
    }

    @Autowired
    public QuizController(QuizRepository quizRepository, UserRepository userRepository) {
        this.quizRepository = quizRepository;
    }

    /* *** POST request mappings *** */

    /* Mapping for adding a quiz */
    @PostMapping(path = "api/quizzes", produces = "application/json")
    public Quiz addQuiz(@Valid @RequestBody Quiz quiz) {

        /* Quiz has to be explicitly set for each option and answer (for FK id purposes) */
        quiz.getOptions().forEach(option -> option.setQuiz(quiz));
        quiz.getAnswers().forEach(answer -> answer.setQuiz(quiz));

        quiz.setUserId(UserService.findUserID(SecurityContextHolder
                .getContext().getAuthentication()
                .getName()));

        System.out.println(quiz);

        quizRepository.save(quiz);

        return quiz;
    }

    /* Mapping solving a quiz */
    @RequestMapping(path = "/api/quizzes/{id}/solve", method = RequestMethod.POST)
    public Feedback answerQuiz(@RequestBody List<Answer> answers,
                               @PathVariable long id) {
        // todo: move all this logic to ? QuizService?
        answers.forEach(System.out::println);

        Optional<Quiz> optionalQuiz = quizRepository.findById(id);
        boolean success = false;

        if (optionalQuiz.isPresent()) {
            Quiz quiz = optionalQuiz.get();
            Set<Integer> dbAnswers = quiz.getAnswers().stream().map(Answer::getAnswer).collect(Collectors.toSet());
            Set<Integer> userAnswers = answers.stream().map(Answer::getAnswer).collect(Collectors.toSet());
            if (dbAnswers.equals(userAnswers)) {
                success = true;
            }
            System.out.println("db answers: " + dbAnswers + "\n" + "user answers: " + userAnswers);
        } else {
            /* If no quiz at Id found */
            throw new QuizNotFoundException((int) id);
        }
        Feedback feedback = new Feedback();
        feedback.setFeedBack(success);
        return feedback;
    }

    /* *** GET request mappings *** */

    /* Mapping for user request GET quiz with ID */
    @RequestMapping(value = "/api/quizzes/{id}", method = RequestMethod.GET)
    public Quiz getQuiz(@PathVariable long id) {
        Optional<Quiz> optionalQuiz = quizRepository.findById(id);
        if (optionalQuiz.isPresent()) {
            return optionalQuiz.get();
        }
        else {
            throw new QuizNotFoundException((int) id);
        }
    }

    /* Mapping for user request GET all quizzes */
    @RequestMapping(value = "/api/quizzes", method = RequestMethod.GET)
    public List<Quiz> getAllQuizzes() {
        Iterable<Quiz> quizIterable = quizRepository.findAll();
        List<Quiz> quizzes = new ArrayList<>();
        quizIterable.forEach(quizzes::add);
        if (quizzes.size() > 0) {
            return quizzes;
        } else {
            throw new QuizNotFoundException("No quizzes found");
        }
    }

    /* DELETE mapping */

    @RequestMapping(value = "api/quizzes/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteQuiz(@PathVariable long id) {

        Optional<Quiz> optionalQuiz = quizRepository.findById(id);
        if (optionalQuiz.isPresent()) {
//            quizRepository.delete(optionalQuiz.get());
            QuizService.deleteQuiz(optionalQuiz.get().getId());
        } else {
            throw new QuizNotFoundException((int) id);
        }
        /* Returns a HTTP response status of 204 No Content */
        return ResponseEntity.noContent()
                .build();
    }
}
