package com.sda.controllers;


import com.sda.exceptions.ResourceNotFoundException;
import com.sda.model.quizzes.Answer;
import com.sda.model.users.Author;
import com.sda.repositories.QuizRepository;
import com.sda.services.AuthorService;
import com.sda.services.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/user")
public class AuthorController {
    @Autowired
    private AuthorService authorService;
    @Autowired
    private QuizService quizService;

    //** Not for API ***
    @GetMapping("/hello")
    @ResponseBody
    public String greet() {
        return "Welcome user";
    }
    // **************** //

//    @PostMapping("/create")
//    public ResponseEntity<Author> createUser (@Valid @RequestBody Author author){
//        authorService.saveNewAuthor(author);
//        return new ResponseEntity<Author>(author, HttpStatus.CREATED);
//    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Author> updateUser (@PathVariable Long id , @RequestBody Author author){
        return authorService.updateAuthor(id, author);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Author> getUserById (@PathVariable Long id ){
        Author author = authorService.findAuthorById(id);
        return new ResponseEntity<Author>(author, HttpStatus.OK);
    }

    @PutMapping("/disable-account/{id}")
    public ResponseEntity<String> removeAuthorUser(@PathVariable long id) {
        authorService.disableAuthor(id);
        return new ResponseEntity<String>("The author with ID " + id + " is removed", HttpStatus.NO_CONTENT);
    }

    // For endpoint testing:
    // Should be excluded once project is finished
    @GetMapping("/view-users")
    public ResponseEntity<List<Author>> getAuthors(){
            return ResponseEntity.ok().body(authorService.getAllAuthors());
    }
}
