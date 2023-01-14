package com.sda.model.users;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sda.model.quizzes.Quiz;
import lombok.*;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Entity @Data @NoArgsConstructor
public class Author implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Firstname cannot be empty!")
    @Pattern(regexp = ("\\b([A-ZÀ-ÿ][-,a-z. ']+[ ]*)+"),message = "The name can not have numbers")
    private String firstName;

    @NotBlank(message = "Lastname cannot be empty!")
    @Pattern(regexp = ("\\b([A-ZÀ-ÿ][-,a-z. ']+[ ]*)+"),message = "The name can not have numbers")
    private String lastName;

    @NotBlank(message = "Username cannot be empty!")
    private String username;
    @Size(min=5 , message = "The password must have at least 5 characters")
    @NotBlank(message = "Password cannot be empty!")

    private String password;

    @Email(message = "Invalid email")
    @NotBlank(message = "Email cannot be empty!")
    private String email;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dateOfBirth;
//
//    @OneToMany
//    List<Quiz> quizzes;

    @Column(columnDefinition="tinyint(1) default 1")
//    @Column(columnDefinition = "boolean default true")
    private boolean isAvailable;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        // Could be used instead of variable isAvailable?
        return true;
    }

}
