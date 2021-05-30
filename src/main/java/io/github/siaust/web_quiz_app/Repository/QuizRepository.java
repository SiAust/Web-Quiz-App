package io.github.siaust.web_quiz_app.Repository;

import io.github.siaust.web_quiz_app.Model.Quiz;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface QuizRepository extends CrudRepository<Quiz, Long>, 
        PagingAndSortingRepository<Quiz, Long> {

        Page<Quiz> findAllByTopic(String topic, Pageable pageable);
}
