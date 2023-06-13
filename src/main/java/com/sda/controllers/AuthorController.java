package com.sda.controllers;

import com.sda.controllers.DTO.AuthorDTO;
import com.sda.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @GetMapping("/{id}")
    public ResponseEntity<AuthorDTO> getUserInfo(@PathVariable long id){
        AuthorDTO authorDTO = authorService.getAuthorInfo(id);
        return new ResponseEntity<>(authorDTO, HttpStatus.OK);
    }
}
