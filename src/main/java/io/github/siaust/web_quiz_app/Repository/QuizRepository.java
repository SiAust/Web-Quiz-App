package io.github.siaust.web_quiz_app.Repository;

import io.github.siaust.web_quiz_app.Model.Completed;
import io.github.siaust.web_quiz_app.Model.Quiz;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;

public interface QuizRepository extends CrudRepository<Quiz, Long>,
        PagingAndSortingRepository<Quiz, Long> {
}
