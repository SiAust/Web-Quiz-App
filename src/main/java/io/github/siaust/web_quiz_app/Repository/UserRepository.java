package io.github.siaust.web_quiz_app.Repository;

import io.github.siaust.web_quiz_app.Model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByEmail(String email);
    Optional<User> findByUserName(String username);

}
