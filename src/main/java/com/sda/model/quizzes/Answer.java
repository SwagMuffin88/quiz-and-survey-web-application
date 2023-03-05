package com.sda.model.quizzes;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity @Data @NoArgsConstructor
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Please write an answer")
    private String statement;

//    @Column(columnDefinition="tinyint(1) default 1")
    private boolean available;

}
