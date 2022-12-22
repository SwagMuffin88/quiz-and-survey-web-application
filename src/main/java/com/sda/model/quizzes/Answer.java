package com.sda.model.quizzes;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity @Data @NoArgsConstructor @RequiredArgsConstructor
@Table(name="answers")
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotBlank(message = "Please write an answer")
    private String statement;

    public Answer(String statement) {
        this.statement = statement;
    }
}
