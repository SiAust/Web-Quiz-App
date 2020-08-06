package io.github.siaust.web_quiz_app.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class UserAccessDenied extends RuntimeException {

    public UserAccessDenied(String message) {
        super(message);
    }
}
