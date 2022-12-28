package com.sda.model.quizzes;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity @Data @NoArgsConstructor
@Table(name = "questions")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int questionId;

//    @NotBlank(message = "Please write a question")
    private String questionStatement;

    private String correctAnswer;

    @OneToMany(targetEntity = Answer.class, mappedBy = "question",
        fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Answer> answers;

    @ManyToOne()
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    public Question(String questionStatement
//            , List<Answer> answers
            , String correctAnswer) {
        this.questionStatement = questionStatement;
//        this.answers = answers;
        this.correctAnswer = correctAnswer;
    }
}
