package io.github.siaust.web_quiz_app.Controller;

import io.github.siaust.web_quiz_app.Model.MyUserDetails;
import io.github.siaust.web_quiz_app.Model.User;
import io.github.siaust.web_quiz_app.Exception.InvalidUserException;
import io.github.siaust.web_quiz_app.Repository.UserRepository;
import io.github.siaust.web_quiz_app.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class UserController {

    /* Register a new user */
    @PostMapping(path = "api/register", produces = "application/json")
    public void addUser(@Valid @RequestBody User user) {
        if (user.getEmail().isEmpty()) {
            throw new InvalidUserException("Passing detail message to the custom exception");
        }
        System.out.println("In the User Controller class");
        System.out.println(user);
        UserService.saveUserToDB(user);
    }
}
