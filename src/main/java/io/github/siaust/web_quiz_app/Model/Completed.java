package io.github.siaust.web_quiz_app.Model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Completed {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private long completedID;

    /* This is the ID of the quiz, column name is ID for Hyperskill tests */
    @Column(name = "id")
    private long id;

    private LocalDateTime completedAt;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // will not send as part of JSON response
    @Column(name = "userid")
    private long userId;

    public Completed() {
    }

    public Completed(long id, LocalDateTime completedAt, long userId) {
        this.id = id;
        this.completedAt = completedAt;
        this.userId = userId;
    }

    public long getCompletedID() {
        return completedID;
    }

    public void setCompletedID(long completedID) {
        this.completedID = completedID;
    }


    public long getId() {
        return id;
    }

    public void setId(long quizId) {
        this.id = quizId;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "id: " + completedID
                + " completedAt: " + completedAt
                + " userID: " + userId;
    }
}
