package io.github.siaust.web_quiz_app.Controller;

import io.github.siaust.web_quiz_app.Model.Answer;
import io.github.siaust.web_quiz_app.Model.Option;
import io.github.siaust.web_quiz_app.Model.Quiz;
import io.github.siaust.web_quiz_app.Repository.QuizRepository;
import io.github.siaust.web_quiz_app.Repository.UserRepository;
import io.github.siaust.web_quiz_app.Service.QuizService;
import jdk.jfr.internal.Options;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Controller
public class TemplateController {

    QuizRepository quizRepository;
    UserRepository userRepository;

    private static final Logger log = LoggerFactory.getLogger(TemplateController.class);
//
//    public TemplateController() {
//        super();
//    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getWelcome(@RequestParam(value = "name", defaultValue = "anon")
                             String name, Model model) {
        model.addAttribute("name", name);

        return "welcome";
    }

    @RequestMapping(value = "/play", method = RequestMethod.GET)
    public String getPlay() {
        return "play";
    }

    /* Shows all the quizzes in the database */
    @RequestMapping(value = "/quiz", method = RequestMethod.GET)
    public String getQuiz(Model model) {
        model.addAttribute("quizzes", quizRepository.findAll());
        return "quiz";
    }

    /* Shows all the users in the database */
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String getUsers(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "users";
    }

    /* Methods for handling create a quiz form */

        /* Landing page to display the initial form */
    @RequestMapping(value = "/create")
    public String createQuizForm(final Quiz quiz) {
//        Quiz quiz = new Quiz();
        List<Option> options = Arrays.asList(new Option(), new Option());
        quiz.setOptions(options);
        List<Answer> answers = Arrays.asList(new Answer());
        quiz.setAnswers(answers);
//        model.addAttribute("quiz", quiz);
        return "create";
    }
        /* Called when user submits form */
    @RequestMapping(value = "/create", params = {"save"})
    public String saveQuizFromForm(@Valid final Quiz quiz, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
//            bindingResult.getFieldErrors().forEach(System.out::println);
            for (Option option : quiz.getOptions()) {
                System.out.println("Option is empty? " + option.getOption().isEmpty());
            }
            bindingResult.getAllErrors().forEach(System.out::println);
            log.info("Binding result error: {}", quiz);
            return "create";
        }
        log.info("Quiz form submitted: {}", quiz); // todo save quiz to db
        return "welcome"; // todo return form submitted result
    }
        /* Called when client selects submit add option button */
    @RequestMapping(value = "/create", params = {"addOption"})
    public String addOptionToForm(@Valid final Quiz quiz, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors("options")) {
            log.info("Binding result error on options");
            bindingResult.getFieldErrors().forEach(fieldError -> System.out.println("Field Error: " + fieldError));
           if (bindingResult.getFieldError("options").getDefaultMessage().startsWith("Must be at least two")) {
               return "create";
           }

            // todo don't add new option, return
        }
        quiz.getOptions().add(new Option());
        return "create";
    }
        /* Called when client selects submit add answer button */
    @RequestMapping(value = "/create", params = {"addAnswer"})
    public String addAnswerToForm(@Valid final Quiz quiz, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors("answers")) {
            log.info("Binding result error on answers");
            bindingResult.getAllErrors().forEach(result -> System.out.println("Error adding answer: " + result));
            if (bindingResult.getFieldError("answers").getDefaultMessage().startsWith("Must be at least one")) {
                return "create";
            }
            // todo don't add new answer, return
        }
        quiz.getAnswers().add(new Answer());
        return "create";
    }

    /* ***** */

    @Autowired
    public void setQuizRepository(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
