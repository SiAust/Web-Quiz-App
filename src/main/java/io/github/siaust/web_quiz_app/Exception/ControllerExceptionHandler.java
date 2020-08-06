package io.github.siaust.web_quiz_app.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.ManyToOne;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(QuizNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleIndexOutOfBounds(Exception e) {
        Map<String, String> response = new HashMap<>();
        response.put("message", e.getMessage());
        response.put("error", "QuizNotFoundException");
        return response;
    }

    @ExceptionHandler(InvalidUserException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleInvalidUserException(Exception e) {
        Map<String, String> response = new HashMap<>();
        response.put("message", e.getMessage());
        response.put("error", "InvalidUserException");
        return response;
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.I_AM_A_TEAPOT) // todo: probably change this
    public Map<String, String> handleUserNameNotFoundException(Exception e) {
        Map<String, String> response = new HashMap<>();
        response.put("message", e.getMessage());
//        response.put("error", "Either bad email or username too short");
        return response;
    }

    @ExceptionHandler(UserAccessDenied.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public Map<String, String> handleUserAccessDenied(Exception e) {
        Map<String, String> response = new HashMap<>();
        response.put("message", e.getMessage());
        return response;
    }

}
