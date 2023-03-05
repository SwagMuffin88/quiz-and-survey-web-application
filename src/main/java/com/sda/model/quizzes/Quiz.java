package com.sda.model.quizzes;

import com.sda.model.users.Author;
import com.sda.model.users.Participant;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity @Data
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Please add a quiz title")
    private String title;
    @NotBlank(message = "Please add a quiz description")
    private String description;

    @OneToMany (cascade = CascadeType.ALL)
    private List<Question> questions;

    @OneToMany (cascade = CascadeType.ALL)
    private List<Participant> participantList;

    @ElementCollection
    private List<String> tags;

    @OneToOne(cascade = CascadeType.MERGE)
    private Author author;

    @NotBlank(message = "Please choose a category")
    private String category;

//    @Column(columnDefinition="tinyint(1) default 1")
    private boolean available;

    private boolean isPublic;

}
