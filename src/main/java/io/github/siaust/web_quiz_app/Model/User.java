package io.github.siaust.web_quiz_app.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Component
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(unique = true)
    @Email(message = "Email be correctly formatted")
    @NotBlank(message = "Email must not be empty") // todo need this with @Email?
    private String email;

    @Column(unique = true)
    @NotEmpty(message = "Username must not be empty")
    @Size(min = 3, message = "Username must be at least 3 characters")
    private String userName;

    @NotEmpty(message = "Password must not be empty")
    @Size(min = 6, message = "Password must be at least 6 characters")
    @JsonIgnore
    private String password;

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

    @Override
    public String toString() {
        return String.format("ID: %d\nEmail: %s\nUsername: %s\nPassword: %s", id, email, userName, password);
    }
}
