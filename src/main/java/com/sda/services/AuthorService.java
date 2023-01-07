package com.sda.services;

import com.sda.model.users.Author;
import com.sda.repositories.AuthorRepository;
import com.sda.repositories.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service @RequiredArgsConstructor @Transactional
public class AuthorService {
    private final AuthorRepository authorRepository;
    private final QuizRepository quizRepository;
    private final PasswordEncoder passwordEncoder;

    public Optional<Author> findAuthor(Long id) {
        return authorRepository.findById(id);
    }
    public void saveNewAuthor(@RequestBody Author author) {
        author.setPassword(passwordEncoder.encode(author.getPassword()));
        author.setActive(true);
        authorRepository.save(author);
    }

    public ResponseEntity<Author> updateAuthor(@RequestBody Long id, Author author) {
        Optional<Author> authorToUpdate = authorRepository.findById(id);
        if(authorToUpdate.isEmpty()){
            return new ResponseEntity<Author>(author, HttpStatus.NOT_FOUND);
        } else {
            authorRepository.save(author);
            return new ResponseEntity<Author>(author, HttpStatus.OK);
        }
    }
    public List<Author> getAuthors(){
        return authorRepository.findAll();
    }


}
