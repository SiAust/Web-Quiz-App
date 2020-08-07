package io.github.siaust.web_quiz_app.Model;

import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Component
@Entity
public class User {

//    @Id
//    @GeneratedValue(strategy =GenerationType.AUTO)
//    Long id;
//    @Column(unique = true, nullable = false)
//    String username;
//    @Column(nullable = false)
//    String password;
//    @Column(nullable = true)
//    String firstname;
//    @Column(nullable = true)
//    String lastname;
//    @Column(nullable = true)
//    String email;
//    @Column(nullable = false)
//    @Enumerated(value = EnumType.STRING)
//    Role role;
//    @Column(nullable = false)
//    Boolean enabled = false;
//    @Column(nullable = false)
//    Date dateCreate = new Date();

    @Column(unique = true)
    @Email
    @NotBlank
    private String email;

    private String password;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User() {}

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User email: " + email + " password: " + password;
    }
}