package com.sda.model.quizzes;

import com.sda.model.users.Author;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity @Data @NoArgsConstructor
@Table(name="quizzes")
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int quizId;

//    @NotBlank(message = "Please add a quiz title")
    private String quizTitle;
    @NotBlank(message = "Please add a quiz description")
    private String quizDescription;

//    @OneToMany(targetEntity = Question.class, mappedBy = "quiz", fetch = FetchType.EAGER,
//        cascade = CascadeType.ALL)
    @OneToMany(orphanRemoval = true)
    @JoinColumn(name="quiz_id")
    private List<Question> questions;

//    @ManyToOne()
//    @JoinColumn(name = "author_id")
//    private Author author;
    private boolean privateStatus;

    public Quiz(String quizTitle, String quizDescription, List<Question> questions, boolean privateStatus) {
        this.quizTitle = quizTitle;
        this.quizDescription = quizDescription;
        this.questions = questions;
        this.privateStatus = privateStatus;
    }
}
