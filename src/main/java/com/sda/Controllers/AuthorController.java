package com.sda.Controllers;

import com.sda.Repositories.AuthorRepository;
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

    @PostMapping("/create")
    public ResponseEntity<Author> createUser ( @RequestBody Author author){
        Author newAuthor = new Author(author.getFirstName(),author.getLastName(), author.getUsername(),author.getPassword(),author.getEmail(),author.getDOB());
        authorRepository.save(newAuthor);
        return new ResponseEntity<Author>(newAuthor, HttpStatus.CREATED);
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<Author> updateUser (@PathVariable int id , @RequestBody Author author){
        Optional<Author> authorToUpdate = authorRepository.findById(id);
        if(authorToUpdate.isPresent()){
            author.setId(id);
            authorRepository.save(author);
            return new ResponseEntity<Author>(author, HttpStatus.OK);
        }else {
            return new ResponseEntity<Author>(author, HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/getAllUsers")
    public ResponseEntity<List<Author>> getAuthors(){
        List<Author> authors = authorRepository.findAll();
        return new ResponseEntity<List<Author>>(authors,HttpStatus.OK);
    }
}
