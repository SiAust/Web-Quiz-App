package io.github.siaust.web_quiz_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

import javax.annotation.Resource;

@SpringBootApplication
public class WebQuizAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebQuizAppApplication.class, args);
    }

}
