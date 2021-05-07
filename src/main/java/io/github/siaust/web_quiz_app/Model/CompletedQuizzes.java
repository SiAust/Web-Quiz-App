package io.github.siaust.web_quiz_app.Model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class CompletedQuizzes {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private long id;

    @ManyToOne
    @JoinColumn
    private User user;

    private boolean wasCorrect;

    private LocalDateTime completedAt;


    public CompletedQuizzes() {
    }

    public CompletedQuizzes(User user, LocalDateTime completedAt, boolean wasCorrect) {
        this.user = user;
        this.completedAt = completedAt;
        this.wasCorrect = wasCorrect;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isWasCorrect() {
        return wasCorrect;
    }

    public void setWasCorrect(boolean wasCorrect) {
        this.wasCorrect = wasCorrect;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }


    @Override
    public String toString() {
        return "id: " + id
                + "\ncompletedAt: " + completedAt
                + "\nUserID: " + user.getId()
                + "\nUserName: " + user.getUserName()
                + "\nwasCorrect: " + wasCorrect;
    }
}
