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

    // todo ***
    public Answer(String answerStatement) {
        this.answerStatement = answerStatement;
    }

    // ************
    // Initial relationship, commented out for now
//    @ManyToOne()
//    @JoinColumn(name = "question_id")
//    private Question question;
    // ************
}
