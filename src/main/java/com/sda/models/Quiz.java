package com.sda.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotBlank(message = "Please add a quiz title")
    private String title;
//    @NotBlank(message = "Please add a quiz description")
    private String description;
    private String  category;
    private List<String> tags;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Question> questions;
    @OneToMany (cascade = CascadeType.ALL)
    private List<Participant> participantList;
    @OneToOne(cascade = CascadeType.MERGE)
    private Author author;
    @Column(columnDefinition="tinyint(1) default 1")
    private boolean available;
    @Column(columnDefinition="tinyint(1) default 1")
    private boolean Public;
}
