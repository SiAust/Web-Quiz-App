package io.github.siaust.web_quiz_app.Service;

import io.github.siaust.web_quiz_app.Model.CompletedQuizzes;
import io.github.siaust.web_quiz_app.Repository.CompletedQuizzesRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class CompletedService {

    private static CompletedQuizzesRepository completedQuizzesRepository;

    public CompletedService(CompletedQuizzesRepository completedQuizzesRepository) {
        CompletedService.completedQuizzesRepository = completedQuizzesRepository;
    }

    public static Page<CompletedQuizzes> getCompletions(int pageNo, int pageSize,
                                                        String sortBy, long userID) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, sortBy));

        return completedQuizzesRepository.findById(pageable, userID);
//        System.out.println(page);

//        return completedRepository.findAll(pageable);
    }

}
