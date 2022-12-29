package com.sda.controllers;



import com.sda.Repositories.AuthorRepository;
import com.sda.model.users.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class AuthorController {

    @Autowired
    AuthorRepository authorRepository;

    @PostMapping("/create")
    public ResponseEntity<Author> createUser (@Valid @RequestBody Author author){
        Author newAuthor = new Author(author.getFirstName(),author.getLastName(), author.getUsername(),author.getPassword(),author.getEmail(),author.getDOB());
        authorRepository.save(newAuthor);
        return new ResponseEntity<Author>(newAuthor, HttpStatus.CREATED);
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<Author> updateUser (@PathVariable int id , @RequestBody Author author){
        Author authorToUpdate = authorRepository.findById(id).
                orElseThrow(()-> new RuntimeException("The user with the ID "+id+" not found "));
        authorToUpdate.setFirstName(author.getFirstName());
        authorToUpdate.setLastName(author.getLastName());
        authorToUpdate.setEmail(author.getEmail());
        authorToUpdate.setUsername(author.getUsername());
        authorToUpdate.setDOB(author.getDOB());
        authorToUpdate.setPassword(author.getPassword());
        authorRepository.save(authorToUpdate);
        return new ResponseEntity<Author>(authorToUpdate, HttpStatus.OK);
    }

    @GetMapping("/getAllUsers")
    public ResponseEntity<List<Author>> getAuthors(){
        List<Author> authors = authorRepository.findAll();
        return new ResponseEntity<List<Author>>(authors,HttpStatus.OK);
    }

    @GetMapping("/getUserById/{id}")
    public ResponseEntity<Author> getAuthorById(@PathVariable int id){
        Optional<Author> author = authorRepository.findById(id);
        if (author.isPresent()){
            Author author1 = author.get();
            return new ResponseEntity<Author>(author1, HttpStatus.OK);
        }else {
            Author author1 = new Author();
            return new ResponseEntity<Author>(author1,HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteUserById(@PathVariable int id){
        authorRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));

        return errors;
    }
}
