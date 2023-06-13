package com.sda.services;

import com.sda.controllers.DTO.AuthorDTO;
import com.sda.controllers.authentication.AuthenticationResponse;
import com.sda.exception.ResourceNotFoundException;
import com.sda.models.Author;
import com.sda.repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {
    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void addAuthor (Author author){
        author.setPassword(passwordEncoder.encode(author.getPassword()));
        authorRepository.save(author);
//        return "added to DB";
    }

    public AuthenticationResponse getAuthenticationResponse(String username) {
        Author author = authorRepository.findByUsername(username).orElseThrow(()-> new ResourceNotFoundException("Quiz no found"));
        return new AuthenticationResponse(author.getId(),author.getUsername());
    }

    public AuthorDTO getAuthorInfo (long id){
        Author author = authorRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Quiz no found"));
        return new AuthorDTO(author.getId(),author.getFirstName(),author.getLastName(),author.getUsername(),author.getEmail(),author.getDateOfBirth());

    }
}
