package com.sda.model.users;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sda.model.quizzes.Quiz;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Entity @Data @NoArgsConstructor
@Table(name="authors")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
//    @Setter(AccessLevel.PROTECTED)
    @Column(name = "author_id")
    private int id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;
    @NotBlank(message = "Username cannot be empty!")
    private String username;
    @Size(min=5 , message = "The password can not be smaller than 5")
    @NotBlank(message = "Password cannot be empty!")
    private String password;

    @Email(message = "Invalid email")
    @NotBlank(message = "Email cannot be empty!")
    private String email;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate DOB;

//    @OneToMany(targetEntity = Quiz.class, mappedBy = "author", fetch = FetchType.EAGER,
//        cascade = CascadeType.ALL)
    @OneToMany(orphanRemoval = true)
    @JoinColumn(name="author_id")
    List<Quiz> quizzes;

    public Author(String firstName, String lastName, String username, String password, String email,
                  LocalDate DOB ) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.email = email;
        this.DOB = DOB;
    }
    public void addQuiz2QuizList(Quiz quiz){ quizzes.add(quiz); }
}
