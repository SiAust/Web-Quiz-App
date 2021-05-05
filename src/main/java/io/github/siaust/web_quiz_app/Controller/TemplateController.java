package io.github.siaust.web_quiz_app.Controller;

import ch.qos.logback.core.boolex.EvaluationException;
import io.github.siaust.web_quiz_app.Model.Answer;
import io.github.siaust.web_quiz_app.Model.Option;
import io.github.siaust.web_quiz_app.Model.Quiz;
import io.github.siaust.web_quiz_app.Model.User;
import io.github.siaust.web_quiz_app.Repository.QuizRepository;
import io.github.siaust.web_quiz_app.Repository.UserRepository;
import io.github.siaust.web_quiz_app.Service.QuizService;
import io.github.siaust.web_quiz_app.Service.UserService;
import jdk.jfr.internal.Options;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.*;

@Controller
public class TemplateController {

    QuizRepository quizRepository;
    UserRepository userRepository;

    @Autowired
    UserService userService;

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
    /* *** Registration handling *** */

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String getRegister(final User user) {
        return "register";
    }

    @RequestMapping(value = "/register", params = {"save"})
    public String saveUser(@Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            return "register";
        }
        user.setRoles("USER");
        userService.saveUserToDB(user);
        return "redirect:/?name=" + user.getUserName();
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    /* *** */

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

    /* *** Methods for handling create a quiz form *** */

    /* Landing page to display the initial form */
    @RequestMapping(value = "/create")
    public String createQuizForm(final Quiz quiz) {
//        Quiz quiz = new Quiz();
        List<Option> options = Arrays.asList(new Option(), new Option());
        quiz.setOptions(options);
        List<Answer> answers = Arrays.asList(new Answer(), new Answer());
        quiz.setAnswers(answers);
//        model.addAttribute("quiz", quiz);
        return "create";
    }
    /* Called when user submits form */
    @RequestMapping(value = "/create", params = {"save"})
    public String saveQuizFromForm(@Valid final Quiz quiz, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.info("Binding result error: {}", quiz);
            bindingResult.getAllErrors().forEach(objectError -> log.info("BindingResult error: {}", objectError));
            return "create";
        }
        /* Answer values at 0 are default values, checkbox was unchecked, and should be removed */
        quiz.getAnswers().removeIf(answer -> answer.getAnswer() == 0);
        quiz.getOptions().forEach(option -> option.setQuiz(quiz));
        quiz.getAnswers().forEach(answer -> answer.setQuiz(quiz));
        /* quiz.setUserId(UserService.findUserID(SecurityContextHolder
                .getContext().getAuthentication()
                .getName()));*/ // todo implement auth
        quiz.setTimestamp(LocalDateTime.now());
        log.info("Quiz form submitted: {}", quiz);
        quizRepository.save(quiz);
        return "result";
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
        }
        quiz.getOptions().add(new Option());
        quiz.getAnswers().add(new Answer());
        return "create";
    }

    @RequestMapping(value = "/create", params = {"removeOption"})
    public String removeOptionFromForm(@Valid final Quiz quiz, BindingResult bindingResult,
                                       HttpServletRequest request) {
        int optionsSize = quiz.getOptions().size();
        if (optionsSize == 2) {
            bindingResult.addError(new FieldError("options", "options"
                    , "Cannot be less than two options"));
            return "create";
        }
        int rowId = Integer.parseInt(request.getParameter("removeOption"));
        quiz.getOptions().remove(rowId);
        quiz.getAnswers().remove(rowId);
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
