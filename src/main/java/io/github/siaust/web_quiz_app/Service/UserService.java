package io.github.siaust.web_quiz_app.Service;

import io.github.siaust.web_quiz_app.Exception.InvalidUserException;
import io.github.siaust.web_quiz_app.Model.MyUserDetails;
import io.github.siaust.web_quiz_app.Model.User;
import io.github.siaust.web_quiz_app.Repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private static UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        UserService.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String email) {

        Optional<User> user = userRepository.findByEmail(email);
        System.out.println("loadByUsername called. User email: " + email);
        System.out.println("Optional user = " + userRepository.findByEmail(email));

        if (user.isPresent()) {
            System.out.println("loadUserByUsername called: user.isPresent() = true");
            return new MyUserDetails(user.get().getEmail(), user.get().getPassword());
        } else {
            throw new UsernameNotFoundException("User: " + email + " not found");
        }
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
    public static void saveUserToDB(User user) {

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
        userRepository.save(user);
    }

    public static User returnUserFromID(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.orElse(null);
    }
}
