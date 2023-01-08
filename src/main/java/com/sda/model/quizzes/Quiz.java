package com.sda.model.quizzes;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity @Data @NoArgsConstructor
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long quizId;

//    @NotBlank(message = "Please add a quiz title")
    private String quizTitle;
    @NotBlank(message = "Please add a quiz description")
    private String quizDescription;

    @OneToMany(orphanRemoval = true)
    @JoinColumn(name="quiz_id")
    private List<Question> questions;

    private boolean isAvailable;


    // ********
    // Initial relationship, commented out for now due to data saving issues
    //    @OneToMany(targetEntity = Question.class, mappedBy = "quiz", fetch = FetchType.EAGER,
//        cascade = CascadeType.ALL)
    //    @ManyToOne()
//    @JoinColumn(name = "author_id")
//    private Author author;
    //*******

    private boolean privateStatus;

    // todo Is the constructor necessary?
    public Quiz(String quizTitle, String quizDescription, List<Question> questions, boolean privateStatus) {
        this.quizTitle = quizTitle;
        this.quizDescription = quizDescription;
        this.questions = questions;
        this.privateStatus = privateStatus;
    }
}
