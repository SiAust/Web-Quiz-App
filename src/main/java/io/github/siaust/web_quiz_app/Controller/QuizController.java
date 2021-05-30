package io.github.siaust.web_quiz_app.Controller;

import io.github.siaust.web_quiz_app.Model.*;
import io.github.siaust.web_quiz_app.Exception.QuizNotFoundException;
import io.github.siaust.web_quiz_app.Repository.CompletedQuizzesRepository;
import io.github.siaust.web_quiz_app.Repository.QuizRepository;
import io.github.siaust.web_quiz_app.Service.CompletedService;
import io.github.siaust.web_quiz_app.Service.QuizService;
import io.github.siaust.web_quiz_app.Service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class QuizController {

    Logger log = LoggerFactory.getLogger(QuizController.class);

    private QuizRepository quizRepository; // todo remove direct call to repo, use QuizService etc
    private CompletedQuizzesRepository completedQuizzesRepository;

    public QuizController() {
    }

    @Autowired
    public QuizController(QuizRepository quizRepository, CompletedQuizzesRepository completedQuizzesRepository) {
        this.quizRepository = quizRepository;
        this.completedQuizzesRepository = completedQuizzesRepository;
    }

    /* *** POST request mappings *** */

    /* Mapping for adding a quiz */
    @PostMapping(path = "api/save-quiz", produces = "application/json")
    public Quiz saveQuiz(@Valid @RequestBody Quiz quiz) {
        log.info("POST new Quiz - {}", quiz);
        /* Quiz has to be explicitly set for each option and answer (for FK id purposes) */
        quiz.getOptions().forEach(option -> option.setQuiz(quiz));
        quiz.getAnswers().forEach(answer -> answer.setQuiz(quiz));

        quiz.setCreatedBy(UserService.findUserByName(SecurityContextHolder
                .getContext().getAuthentication()
                .getName()));
        quiz.setTimestamp(LocalDateTime.now());
        quizRepository.save(quiz);
        log.info("POST request: api/quizzes Body: " + quiz);
        return quiz;
    }

    /* Mapping solving a quiz */
    @RequestMapping(path = "/api/quizzes/{id}/solve", method = RequestMethod.POST)
    public Feedback answerQuiz(@RequestBody List<Answer> answers,
                               @PathVariable long id) {
        // todo: move all this logic to ? QuizService?
//        answers.forEach(System.out::println);

        Optional<Quiz> optionalQuiz = quizRepository.findById(id);
        boolean success;
        Set<Integer> dbAnswers;

        if (optionalQuiz.isPresent()) {
            Quiz quiz = optionalQuiz.get();
            dbAnswers = quiz.getAnswers().stream().map(Answer::getAnswer).collect(Collectors.toSet());
            Set<Integer> userAnswers = answers.stream().map(Answer::getAnswer).collect(Collectors.toSet());

            /* Get the current username from logged in user */
            User user = UserService.findUserByName(SecurityContextHolder.getContext()
                    .getAuthentication().getName());

            success = dbAnswers.equals(userAnswers);
            /* Save a completion of the quiz to the COMPLETED_QUIZZES table */
            completedQuizzesRepository.save(new CompletedQuizzes(user, LocalDateTime.now(), success));
            log.info("db answers: " + dbAnswers + "\nuser answers: " + userAnswers);
        } else {
            /* If no quiz at Id found */
            throw new QuizNotFoundException((int) id);
        }

        return new Feedback(success,
                !dbAnswers.isEmpty() ? dbAnswers.stream().mapToInt(Integer::intValue).toArray() : new int[]{});
    }

    /* *** GET request mappings *** */

    /* Mapping for user request GET quiz with ID */
    @RequestMapping(value = "/api/quizzes/{id}", method = RequestMethod.GET)
    public Quiz getQuiz(@PathVariable long id) {
        Optional<Quiz> optionalQuiz = quizRepository.findById(id);
        if (optionalQuiz.isPresent()) {
            return optionalQuiz.get();
        } else {
            throw new QuizNotFoundException((int) id);
        }
    }

    /* Mapping for user request GET all quizzes */ // todo: add requestParams for paging
    @RequestMapping(value = "/api/quizzes", method = RequestMethod.GET)
    public Page<Quiz> getAllQuizzes(
            @RequestParam(defaultValue = "RANDOM") String topic,
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "id") String sortBy) {

        log.info(
            "\ntopic: " + topic +
            "\npageNo: " + pageNo + 
            "\npageSize: " + pageSize + 
            "\nsortBy: " + sortBy);

        return QuizService.getQuizzes(topic, pageNo, pageSize, sortBy);
    }

    /* TODO: Remove */
    @RequestMapping(value = "/api/quizzes/completed", method = RequestMethod.GET)
    public Page<CompletedQuizzes> getAllCompletions(@RequestParam(defaultValue = "0") int pageNo,
                                                    @RequestParam(defaultValue = "10") int pageSize,
                                                    @RequestParam(defaultValue = "completed_at") String sortBy) {

        // this is the current user. Use this as a param when getting completed quizzes by id
        String currentUser = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();
        long userID = UserService.findUserID(currentUser);

        return CompletedService.getCompletions(pageNo, pageSize, sortBy, userID);
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
