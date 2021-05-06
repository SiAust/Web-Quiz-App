package io.github.siaust.web_quiz_app.Controller;

import io.github.siaust.web_quiz_app.Model.User;
import io.github.siaust.web_quiz_app.Exception.InvalidUserException;
import io.github.siaust.web_quiz_app.Service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class UserController {

    UserService userService;

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /* Register a new user */
    @PostMapping(path = "api/register", produces = "application/json")
    public User addUser(@Valid @RequestBody User user, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            throw new InvalidUserException("Bad user creation"); // todo add errors to Json map
        }
        log.info("User saved to DB from /api/register: User - {}", user);
        return userService.saveUserToDB(user);
    }
}
