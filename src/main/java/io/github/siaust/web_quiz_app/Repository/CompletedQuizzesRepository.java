package io.github.siaust.web_quiz_app.Repository;

import io.github.siaust.web_quiz_app.Model.CompletedQuizzes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CompletedQuizzesRepository extends CrudRepository<CompletedQuizzes, Long>,
        PagingAndSortingRepository<CompletedQuizzes, Long> {

    @Query(value = "select * from completed where userId = :userid", nativeQuery = true)
    Page<CompletedQuizzes> findById(Pageable pageable,
                                    @Param("userid") long userid);

    List<CompletedQuizzes> findAllByUser_id(long userID);

    Long countAllByUser_id(long userID);
    Long countAllByUser_idAndWasCorrectTrue(long userID);
}
