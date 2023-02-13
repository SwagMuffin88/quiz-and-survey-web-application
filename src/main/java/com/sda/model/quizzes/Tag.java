package com.sda.model.quizzes;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Data
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Cannot add an empty tag!")
    private String value;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Quiz> taggedQuizzes;
}
