package com.sda.model.quizzes;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity @Data @NoArgsConstructor
@Table(name = "questions")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotBlank(message = "Please write a question")
    private String statement;

    @OneToMany
    private List<Answer> answers;

    public Question(String statement, List<Answer> answers) {
        this.statement = statement;
        this.answers = answers;
    }
}
