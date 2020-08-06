package io.github.siaust.web_quiz_app.Service;

import io.github.siaust.web_quiz_app.Exception.UserAccessDenied;
import io.github.siaust.web_quiz_app.Model.Quiz;
import io.github.siaust.web_quiz_app.Repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
            + " Quiz userID: " + optionalQuiz.get().getUserId()
            + " logged in userID: " + id);
            if (id == optionalQuiz.get().getUserId()) {
                quizRepository.delete(optionalQuiz.get());
            } else {
                throw new UserAccessDenied("User does not have permission to delete quiz.");
            }
        }

    }

    /*@Autowired
    public static void setQuizRepository(QuizRepository quizRepository) {
        QuizService.quizRepository = quizRepository;
    }*/
}
