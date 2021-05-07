package io.github.siaust.web_quiz_app.Service;

import io.github.siaust.web_quiz_app.Exception.InvalidUserException;
import io.github.siaust.web_quiz_app.Model.User;
import io.github.siaust.web_quiz_app.Repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private static UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        UserService.userRepository = userRepository;
    }

    /** Tries to find the user in the database by username.
     * @param username the username of the User (email)
     * @return the automatically assigned ID of the User or -1 if no matching User. */
    public static long findUserID(String username) { // todo change from static?
        Optional<User> optionalUser = userRepository.findByEmail(username);
        return optionalUser.map(User::getId).orElse(-1L);
    }


    /** Validates a User before persisting that User to the USER_TABLE.
     * @param user The User to be validated and persisted. */
    public User saveUserToDB(User user) {
        if (user.getPassword().length() < 5) {
            throw new InvalidUserException("password too short.");
        }

        // No internal emails allowed (name@address), must be name@address.com ex
        String[] email = user.getEmail().split("@");
        if (!email[1].contains(".")) {
            throw new InvalidUserException("email format must not be internal");
        }

        // Checks if a user has already registered under this email id
        if ((userRepository.findByEmail(user.getEmail()).isPresent())) {
            throw new InvalidUserException("user already exists");
        }

        logger.info("User " + user.getEmail() + " registered");
        return userRepository.save(user); // todo null check?
    }

    public static User findUserByName(String name) {
        Optional<User> user = userRepository.findByUserName(name);
        return user.orElse(null);
    }

    public static User findUserByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.orElse(null);
    }
}
