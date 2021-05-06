package io.github.siaust.web_quiz_app.Security;

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
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    private static final Logger log = LoggerFactory.getLogger(MyUserDetailsService.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUserName(username);
        log.info("loadByUsername called. User username: " + username);
        log.info("Optional<User>.isPresent() = " + user.isPresent());

        if (user.isPresent()) {
            return new MyUserDetails(user.get());
        } else {
            throw new UsernameNotFoundException("User: " + username + " not found");
        }
    }

}
