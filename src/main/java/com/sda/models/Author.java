package com.sda.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Pattern(regexp = ("\\b([A-ZÀ-ÿ][-,a-z. ']+[ ]*)+"),
            message = "The First name can only contain letters and the first letter must start with an uppercase")
    private String firstName;

    @Pattern(regexp = ("\\b([A-ZÀ-ÿ][-,a-z. ']+[ ]*)+"),
            message = "The Last name can only contain letters and the first letter must start with an uppercase")
    private String lastName;

    @Column(name = "username", unique=true)
    private String username;

    private String password;

    @Column(name = "email", unique=true)
    @Email(message = "Invalid email")
    private String email;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;

    @Column(columnDefinition="tinyint(1) default 1")
    private boolean available;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles;
}
