package io.github.siaust.web_quiz_app.Repository;

import io.github.siaust.web_quiz_app.Model.Completed;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface CompletedRepository extends CrudRepository<Completed, Long>,
        PagingAndSortingRepository<Completed, Long> {

    @Query(value = "select * from completed where userId = :userid", nativeQuery = true)
    Page<Completed> findById(Pageable pageable,
                             @Param("userid") long userid);
}
