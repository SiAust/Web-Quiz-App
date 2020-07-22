package io.github.siaust.web_quiz_app;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(IndexOutOfBoundsException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleIndexOutOfBounds(Exception e) {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Quiz does not exist");
        response.put("error", "IndexOutOfBounds");
        return response;
    }

}
