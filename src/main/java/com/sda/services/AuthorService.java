package com.sda.services;

import com.sda.exceptions.ResourceNotFoundException;
import com.sda.model.users.Author;
import com.sda.repositories.AuthorRepository;
import com.sda.repositories.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service @RequiredArgsConstructor @Transactional
public class AuthorService {
    private final AuthorRepository authorRepository;
    private final QuizRepository quizRepository;
    private final PasswordEncoder passwordEncoder;

    public Author findAuthorById(Long id) {
        Optional<Author> author = authorRepository.findById(id);
        return author.orElseThrow(()-> new ResourceNotFoundException("The user with the ID "+id+" not found "));
    }

    public Author findAuthor(String username) {
        return authorRepository.findByUsername(username);
    }

    public void saveNewAuthor(Author author) {
        if (findAuthor(author.getUsername()) == null) {
            author.setPassword(passwordEncoder.encode(author.getPassword()));
            author.setActive(true);
            authorRepository.save(author);
        } else {
            throw new ResourceNotFoundException("Username is already in use");
        }
    }
    public ResponseEntity<Author> updateAuthor( Long id, Author author) {
        Author authorToUpdate = authorRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("The user with the ID "+id+" not found "));
        authorToUpdate.setLastName(author.getLastName());
        authorToUpdate.setFirstName(author.getFirstName());
        authorToUpdate.setPassword(author.getPassword());
        authorToUpdate.setUsername(author.getUsername());
        authorToUpdate.setActive(author.isActive());
        authorToUpdate.setDateOfBirth(author.getDateOfBirth());
        authorRepository.save(authorToUpdate);
        return new ResponseEntity<Author>(authorToUpdate, HttpStatus.OK);


//        Optional<Author> authorToUpdate = authorRepository.findById(id);
//         if(authorToUpdate.isPresent()){
//             authorRepository.save(author);
//             return new ResponseEntity<Author>(author, HttpStatus.OK);
//         }else {
//             throw new ResourceNotFoundException("The id "+id+" does not exist");
//        }
    }

    public List<Author> getAuthors(){
        return authorRepository.findAll();
    }


}
