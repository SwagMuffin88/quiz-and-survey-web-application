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

    @OneToMany(orphanRemoval = true)
    @JoinColumn(name="question_id")
    private List<Answer> answers;

    // **********
    // Initial relationship, commented out for now due to data saving issues
//    @OneToMany(targetEntity = Answer.class, mappedBy = "question",
//        fetch = FetchType.EAGER, cascade = CascadeType.ALL)

    //    @ManyToOne()
//    @JoinColumn(name = "quiz_id")
//    private Quiz quiz;
    // **********

    public Question(String questionStatement, String correctAnswer
//            , List<Answer> answers
            ) {
        this.questionStatement = questionStatement;
//        this.answers = answers;
        this.correctAnswer = correctAnswer;
    }
}
