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

    @OneToMany
    private List<Question> questions;

    private boolean isAvailable;

    private boolean privateStatus;

}
