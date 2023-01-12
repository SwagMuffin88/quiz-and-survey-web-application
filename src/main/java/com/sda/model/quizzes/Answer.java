package com.sda.model.quizzes;

import lombok.*;

import javax.persistence.*;

@Entity @Data @NoArgsConstructor
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int answerId;

//    @NotBlank(message = "Please write an answer")
    private String answerStatement;

    private boolean isAvailable;

}
