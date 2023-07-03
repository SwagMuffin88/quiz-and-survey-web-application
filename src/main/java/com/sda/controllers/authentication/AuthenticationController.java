package com.sda.controllers.authentication;

import com.sda.controllers.DTO.AuthorDTO;
import com.sda.exception.ResourceNotFoundException;
import com.sda.models.Author;
import com.sda.services.AuthorService;
import jakarta.validation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthenticationController {
    @Autowired
    private AuthorService authorService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<AuthorDTO> registerAuthor(@Valid @RequestBody Author author){
        author.setAvailable(true);
        authorService.addAuthor(author);
         AuthorDTO authorDTO = new AuthorDTO(author.getId(), author.getFirstName(), author.getLastName(),
                 author.getUsername(), author.getEmail(), author.getDateOfBirth(), author.isAvailable());
        return new ResponseEntity<>(authorDTO, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticateAuthor(@Valid @RequestBody AuthenticationRequest authenticationRequest ){
        Authentication authentication= authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),authenticationRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        AuthenticationResponse authenticationResponse = authorService.getAuthenticationResponse(authenticationRequest.getUsername());
        return new ResponseEntity<>(authenticationResponse,HttpStatus.OK);
    }
    @GetMapping("/test")
    public ResponseEntity<String> test(){

        throw new ResourceNotFoundException("nod");
    }

}
