package com.sda.model.quizzes;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity @Data @NoArgsConstructor
@Table(name="quizzes")
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotBlank(message = "Please add a quiz title")
    private String title;
    @NotBlank(message = "Please add a quiz description")
    private String description;

    @OneToMany
    private List<Question> questions;

    public Quiz(String title, String description, List<Question> questions) {
        this.title = title;
        this.description = description;
        this.questions = questions;
    }
}
