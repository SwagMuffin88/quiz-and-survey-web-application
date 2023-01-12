package com.sda.model.quizzes;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity @Data @NoArgsConstructor
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long questionId;

//    @NotBlank(message = "Please write a question")
    private String questionStatement;

    private String correctAnswer;

    @OneToMany
    private List<Answer> answers;

    private boolean isAvailable;

}
