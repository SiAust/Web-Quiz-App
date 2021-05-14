package io.github.siaust.web_quiz_app.Service;

import io.github.siaust.web_quiz_app.Exception.UserAccessDenied;
import io.github.siaust.web_quiz_app.Model.Quiz;
import io.github.siaust.web_quiz_app.Repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class QuizService {

    private static QuizRepository quizRepository;

    @Autowired
    public QuizService(QuizRepository quizRepository) {
        QuizService.quizRepository = quizRepository;
    }

    public static void addQuiz() {
        // todo: implement this? Does adding quizzes need further validation or encapsulation?
    }

    /** Returns all the quizzes in the database. May return a paginated content if there is
     * enough entries in the list?
     * @return A List of Quiz objects
     * @param pageNo The current page of returned items out of the set
     * @param pageSize Number of items in a page
     * @param sortBy The method of sorting, by "ID", any other column property I guess
     * */
    public static Page<Quiz> getQuizzes(int pageNo, int pageSize, String sortBy) {

        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        return quizRepository.findAll(pageable);

//        if (pagedResult.hasContent()) {
//            return pagedResult;
//        } else {
//            throw new QuizNotFoundException("No quizzes to pages?");
//        }
    }

    /** @param  quizId id of the Quiz
     *  @throws  UsernameNotFoundException if the authenticated User
     *  does not match the user ID of the Quiz. */
    public static void deleteQuiz(long quizId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(auth.getName());

        long id = UserService.findUserID(auth.getName());
        System.out.println("User id: " + id );
        if (id == -1) {
            System.out.println("User id not found: -1");
            throw new UserAccessDenied("No matching ID to delete");
        }
        Optional<Quiz> optionalQuiz = quizRepository.findById(quizId);

        if (optionalQuiz.isPresent()) {
            System.out.println("Quiz is present. Quiz ID: " + optionalQuiz.get().getId()
            + " Quiz userID: " + optionalQuiz.get().getCreatedBy().getId()
            + " logged in userID: " + id);
            if (id == optionalQuiz.get().getCreatedBy().getId()) {
                quizRepository.delete(optionalQuiz.get());
            } else {
                throw new UserAccessDenied("User does not have permission to delete quiz.");
            }
        }

    }
}
