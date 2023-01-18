package com.sda.controllers.AuthenticationController;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @NotBlank(message = "Firstname cannot be empty!")
    @Pattern(regexp = ("\\b([A-ZÀ-ÿ][-,a-z. ']+[ ]*)+"),message = "Please make sure that the name format is correct and that the first letter is Uppercase")
    private String firstName;

    @NotBlank(message = "Lastname cannot be empty!")
    @Pattern(regexp = ("\\b([A-ZÀ-ÿ][-,a-z. ']+[ ]*)+"),message = "Please make sure that the name format is correct and that the first letter is Uppercase")
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
}
