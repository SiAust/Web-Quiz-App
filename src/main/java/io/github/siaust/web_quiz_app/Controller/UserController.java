package io.github.siaust.web_quiz_app.Controller;

import io.github.siaust.web_quiz_app.Model.MyUserDetails;
import io.github.siaust.web_quiz_app.Model.User;
import io.github.siaust.web_quiz_app.Exception.InvalidUserException;
import io.github.siaust.web_quiz_app.Repository.UserRepository;
import io.github.siaust.web_quiz_app.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    /* Register a new user */
    @PostMapping(path = "api/register", produces = "application/json")
    public User addUser(@Valid @RequestBody User user, BindingResult bindingResult) {
        System.out.println("BindingResult hasGlobalErrors: " + bindingResult.hasGlobalErrors());
        if (bindingResult.hasFieldErrors()) {// todo use BindingResult?
            bindingResult.getFieldErrors().forEach(System.out::println);
        }
        if (user.getEmail().isEmpty()) {
            throw new InvalidUserException("Passing detail message to the custom exception");
        }
        System.out.println("In the User Controller class");
        System.out.println(user);
        return userService.saveUserToDB(user);
    }
}
