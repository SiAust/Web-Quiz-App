package io.github.siaust.web_quiz_app.Model;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;

@Component
@Entity
@SequenceGenerator(name = "userSeq", initialValue = 2)
@Table(name = "user_table")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userSeq")
    private long id;

    @Column(unique = true)
    @Email(message = "Email be correctly formatted")
    @NotBlank(message = "Email must not be empty") // todo need this with @Email?
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String email;

    @Column(unique = true) // todo catch exception
    @NotEmpty(message = "Username must not be empty")
    @Size(min = 3, max = 12, message = "Username must be between 3 and 12 characters")
    private String userName;

    @NotEmpty(message = "Password must not be empty")
    @Size(min = 6, message = "Password must be at least 6 characters")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String roles;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<CompletedQuizzes> completedQuizzes;

    @OneToMany(mappedBy = "createdBy", cascade = CascadeType.ALL)
    private List<Quiz> quizzes;

    public User(String email, String userName, String password) {
        this.email = email;
        this.userName = userName;
        this.password = password;
    }

    public User() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return String.format("ID: %d\nEmail: %s\nUsername: %s\nPassword: %s", id, email, userName, password);
    }
}
