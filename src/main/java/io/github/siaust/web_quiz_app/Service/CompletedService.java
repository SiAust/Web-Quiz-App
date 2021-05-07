package io.github.siaust.web_quiz_app.Service;

import io.github.siaust.web_quiz_app.Model.Completed;
import io.github.siaust.web_quiz_app.Repository.CompletedRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class CompletedService {

    private static CompletedRepository completedRepository;

    public CompletedService(CompletedRepository completedRepository) {
        CompletedService.completedRepository = completedRepository;
    }

    public static Page<Completed> getCompletions(int pageNo, int pageSize,
                                                 String sortBy, long userID) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, sortBy));

        return completedRepository.findById(pageable, userID);
//        System.out.println(page);

//        return completedRepository.findAll(pageable);
    }

}
