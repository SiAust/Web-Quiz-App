package io.github.siaust.web_quiz_app.Service;

import io.github.siaust.web_quiz_app.Exception.InvalidUserException;
import io.github.siaust.web_quiz_app.Model.MyUserDetails;
import io.github.siaust.web_quiz_app.Model.User;
import io.github.siaust.web_quiz_app.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsByNameServiceWrapper;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserService implements UserDetailsService {

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
    public static long findUserID(String username) {
        Optional<User> optionalUser = userRepository.findByEmail(username);
        return optionalUser.map(User::getId).orElse(-1L);
    }


    /** Validates a User before persisting that User to the USER_TABLE.
     * @param user The User to be validated and persisted. */
    public static void saveUserToDB(User user) {

        if (user.getPassword().length() < 5) {
            throw new InvalidUserException("password too short.");
        }

        // Is this handled by @Email validation?
        /*Pattern pattern = Pattern.compile("simon\\.aust@hotmail\\.com");
        Matcher matcher = pattern.matcher(user.getEmail());

        if (!matcher.matches()) {
            throw new InvalidUserException("email not correct format");
        }*/

        userRepository.save(user);
    }
}
