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

    @OneToMany(orphanRemoval = true)
    @JoinColumn(name="question_id")
    private List<Answer> answers;

    private boolean isAvailable;


    public Question(String questionStatement, String correctAnswer, List<Answer> answers) {
        this.questionStatement = questionStatement;
        this.answers = answers;
        this.correctAnswer = correctAnswer;
    }
}
