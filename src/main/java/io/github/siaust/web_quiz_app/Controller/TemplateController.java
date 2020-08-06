package io.github.siaust.web_quiz_app.Controller;

import io.github.siaust.web_quiz_app.Model.Quiz;
import io.github.siaust.web_quiz_app.Repository.QuizRepository;
import io.github.siaust.web_quiz_app.Repository.UserRepository;
import io.github.siaust.web_quiz_app.Service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class TemplateController {

    QuizRepository quizRepository;
    UserRepository userRepository;

    /* Test for Thymeleaf */
    /* The model is a set of key value pairs that Thymeleaf refers
    * to in the HTML code to insert these values */
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

    /* Shows all the users in the databse */
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String getUsers(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "users";
    }

    @Autowired
    public void setQuizRepository(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
