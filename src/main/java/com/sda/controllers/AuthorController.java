package com.sda.controllers;


import com.sda.model.quizzes.Question;
import com.sda.model.quizzes.Quiz;
import com.sda.repositories.AuthorRepository;
import com.sda.exceptions.ResourceNotFoundException;

import com.sda.model.users.Author;
import com.sda.repositories.AuthorRepository;
import com.sda.repositories.QuizRepository;
import com.sda.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class AuthorController {
    @Autowired
    AuthorService authorService;
    @Autowired
    private QuizRepository quizRepository;

    //** Not for API ***
    @GetMapping("/hello")
    @ResponseBody
    public String greet() {
        return "Welcome user";
    }
    // **************** //

    @PostMapping("/create")
    public ResponseEntity<Author> createUser (@Valid @RequestBody Author author){
        authorService.saveNewAuthor(author);
        return new ResponseEntity<Author>(author, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Author> updateUser (@PathVariable Long id , @RequestBody Author author){
        return authorService.updateAuthor(id, author);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Author> getUserById (@PathVariable Long id ){
        Author author =authorService.findAuthorById(id);
        return new ResponseEntity<Author>(author,HttpStatus.OK);
    }


    // For endpoint testing:
    @GetMapping("/view-users")
    public ResponseEntity<List<Author>> getAuthors(){
        return ResponseEntity.ok().body(authorService.getAuthors());
    }
}
