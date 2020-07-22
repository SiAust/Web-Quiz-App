package io.github.siaust.web_quiz_app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class QuizController {

    private QuizRepository quizRepository;

    public QuizController() {
    }

    @Autowired
    public QuizController(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    /* *** POST request mappings *** */

    /* Mapping for adding a quiz POST */
    @PostMapping(path = "api/quizzes", produces = "application/json")
    public Quiz addQuiz(@Valid @RequestBody Quiz quiz) {

        /* Quiz has to be explicitly set for each option and answer (for FK id purposes) */
        quiz.getOptions().forEach(option -> option.setQuiz(quiz));
        quiz.getAnswers().forEach(answer -> answer.setQuiz(quiz));

        System.out.println(quiz);

        quizRepository.save(quiz);

        return quiz;
    }

    /* Mapping for user answer attempt POST */
    @RequestMapping(path = "/api/quizzes/{id}/solve", method = RequestMethod.POST)
    public Feedback answerQuiz(@RequestBody List<Answer> answers,
                               @PathVariable long id) {
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
            throw new IndexOutOfBoundsException();
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
            throw new IndexOutOfBoundsException();
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
            throw new IndexOutOfBoundsException();
        }
    }
}
