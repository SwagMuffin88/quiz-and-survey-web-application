package com.sda.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Please write a question")
    private String statement;

    @OneToOne(cascade = CascadeType.MERGE)
    private Answer correctAnswer;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Answer> answers;

    @Column(columnDefinition="tinyint(1) default 1")
    private boolean available;
}
