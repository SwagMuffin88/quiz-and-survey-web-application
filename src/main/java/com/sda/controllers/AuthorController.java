package com.sda.controllers;

import com.sda.Repositories.AuthorRepository;
import com.sda.exceptions.ResourceNotFoundException;
import com.sda.model.users.Author;
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
    AuthorRepository authorRepository;

//    @Autowired
//    public AuthorController(AuthorRepository authorRepository) {
//        this.authorRepository = authorRepository;
//    }

    @GetMapping("/hello")
    @ResponseBody
    public String greet() {
        return "Welcome user";
    }

    @PostMapping("/create")
    public ResponseEntity<Author> createUser (@Valid @RequestBody Author author){
        author.setActive(true);
        authorRepository.save(author);
        return new ResponseEntity<Author>(author, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Author> updateUser (@PathVariable Long id , @RequestBody Author author){
        Optional<Author> authorToUpdate = authorRepository.findById(id);

        if(authorToUpdate.isPresent()){
            authorRepository.save(author);
            return new ResponseEntity<Author>(author, HttpStatus.OK);
        }else {
            throw new ResourceNotFoundException("The id "+id+" does not exsist ");
        }
    }

    @GetMapping
    public ResponseEntity<List<Author>> getAuthors(){
        List<Author> authors = authorRepository.findAll();
        return new ResponseEntity<List<Author>>(authors,HttpStatus.OK);
    }


}
