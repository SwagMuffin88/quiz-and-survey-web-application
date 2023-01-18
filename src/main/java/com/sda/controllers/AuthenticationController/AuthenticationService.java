package com.sda.controllers.AuthenticationController;

import com.sda.config.JwtService;
import com.sda.model.users.Author;
import com.sda.services.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final AuthorService authorService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse regiter(RegisterRequest request) {
        Author author = new Author();
        author.setUsername(request.getUsername());
        author.setAvailable(true);
        author.setPassword(passwordEncoder.encode(request.getPassword()));
        author.setFirstName(request.getFirstName());
        author.setLastName(request.getLastName());
        author.setEmail(request.getEmail());
        author.setDateOfBirth(request.getDateOfBirth());
        authorService.saveNewAuthor(author);
        var jwtToken = jwtService.generateToken(author);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword()));
        Author author = authorService.findAuthorByUserName(request.getUsername());
        var jwtToken = jwtService.generateToken(author);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }
}
