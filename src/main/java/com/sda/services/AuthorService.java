package com.sda.services;

import com.sda.controllers.DTO.AuthorDTO;
import com.sda.controllers.authentication.AuthenticationResponse;
import com.sda.exception.ResourceNotFoundException;
import com.sda.models.Author;
import com.sda.repositories.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorService {
    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void addAuthor (Author author){
        author.setPassword(passwordEncoder.encode(author.getPassword()));
        authorRepository.save(author);
    }

    public AuthenticationResponse getAuthenticationResponse(String username) {
        Author author = authorRepository.findByUsername(username).orElseThrow(
                ()-> new ResourceNotFoundException("Author not found"));
        return new AuthenticationResponse(author.getId(),author.getUsername());
    }

    public AuthorDTO getAuthorInfo (long id){
        Author author = authorRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Author not found"));
        author.setAvailable(true);
        return new AuthorDTO(author.getId(),author.getFirstName(),author.getLastName(),author.getUsername(),
                author.getEmail(),author.getDateOfBirth(), author.isAvailable());

    }

    public AuthorDTO updateAuthor(Long id, Author author) {

        Author authorToUpdate = authorRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("The user with the ID "+id+" not found "));

        authorToUpdate.setFirstName(author.getFirstName());
        authorToUpdate.setLastName(author.getLastName());
        authorToUpdate.setUsername(author.getUsername());
        authorToUpdate.setPassword(passwordEncoder.encode(author.getPassword()));
        authorToUpdate.setEmail(author.getEmail());
        authorToUpdate.setAvailable(true);
        authorToUpdate.setDateOfBirth(author.getDateOfBirth());
        authorRepository.saveAndFlush(authorToUpdate);

        return new AuthorDTO(authorToUpdate.getId(), authorToUpdate.getFirstName(),
                authorToUpdate.getLastName(), authorToUpdate.getUsername(), authorToUpdate.getEmail(),
                authorToUpdate.getDateOfBirth(), authorToUpdate.isAvailable());
    }

    public Author findAuthorById(Long id) {
        // Finds all authors in the database regardless of availability
        Optional<Author> author = authorRepository.findById(id);
        return author.orElseThrow(()-> new ResourceNotFoundException(
                "The user with the ID " + id + " not found "));
    }

    public void disableAuthor(long id) {
        Author author = findAuthorById(id);
        author.setAvailable(false);
        authorRepository.save(author);
    }
}
